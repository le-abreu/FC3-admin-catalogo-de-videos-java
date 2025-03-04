package com.fullcycle.admin.catalogo.infrastructure.castmember;

import com.fullcycle.admin.catalogo.domain.castmember.CastMember;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import com.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.fullcycle.admin.catalogo.infrastructure.utils.SpecificationUtils.like;

@Component
public class CastMemberMySQLGateway implements CastMemberGateway {


    private final CastMemberRepository repository;

    public CastMemberMySQLGateway(final CastMemberRepository repository) {
        this.repository = repository;
    }


    @Override
    public CastMember create(final CastMember aCastMember) {
        return save(aCastMember);
    }

    @Override
    public CastMember update(final CastMember aCastMember) {
        return save(aCastMember);
    }

    private CastMember save(CastMember aCastMember) {
        return this.repository.save(CastMemberJpaEntity.from(aCastMember)).toAggregate();
    }

    @Override
    public void deleteById(CastMemberID anId) {
        final var anIdValue = anId.getValue();
        if (this.repository.existsById(anIdValue)) {
            this.repository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<CastMember> findById(final CastMemberID anId) {
        return this.repository.findById(anId.getValue())
                .map(CastMemberJpaEntity::toAggregate);
    }


    @Override
    public Pagination<CastMember> findAll(final SearchQuery aQuery) {
        // Paginação
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        // Busca dinamica pelo criterio terms (name ou description)
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CastMemberJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<CastMemberID> existsByIds(final Iterable<CastMemberID> castMemberIDs) {
        final var ids = StreamSupport.stream(castMemberIDs.spliterator(), false)
                .map(CastMemberID::getValue)
                .toList();
        return this.repository.existsByIds(ids).stream()
                .map(CastMemberID::from)
                .toList();
    }

    private Specification<CastMemberJpaEntity> assembleSpecification(final String str) {
        return like("name", str);
    }
}
