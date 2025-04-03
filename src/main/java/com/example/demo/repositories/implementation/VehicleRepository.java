package com.example.demo.repositories.implementation;

import com.example.demo.entities.VehicleEntity;
import com.example.demo.exceptions.VehicleNotFoundException;
import com.example.demo.filters.VehicleFilter;
import com.example.demo.mappers.VehicleMapper;
import com.example.demo.model.VehicleModel;
import com.example.demo.repositories.abstraction.VehicleRepositoryInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class VehicleRepository implements VehicleRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    private final VehicleMapper vehicleMapper;

    public VehicleRepository(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    @Transactional
    public Long create(VehicleEntity vehicleEntity) {
        VehicleModel model = vehicleMapper.toModel(vehicleEntity);
        entityManager.persist(model);
        entityManager.flush();
        return model.getId();
    }

    @Override
    public List<VehicleEntity> findByFilter(VehicleFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleModel> query = cb.createQuery(VehicleModel.class);
        Root<VehicleModel> root = query.from(VehicleModel.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.model() != null) {
            predicates.add(cb.like(cb.lower(root.get("model")), "%" + filter.model().toLowerCase() + "%"));
        }

        if (filter.brand() != null) {
            predicates.add(cb.like(cb.lower(root.get("brand")), "%" + filter.brand().toLowerCase() + "%"));
        }

        if (filter.year() != null) {
            predicates.add(cb.equal(root.get("year"), filter.year()));
        }

        if (filter.description() != null) {
            predicates.add(cb.like(cb.lower(root.get("description")), "%" + filter.description().toLowerCase() + "%"));
        }

        if (filter.sold() != null) {
            predicates.add(cb.equal(root.get("sold"), filter.sold()));
        }

        if (filter.created() != null) {
            predicates.add(cb.equal(root.get("created"), filter.created()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<VehicleModel> typedQuery = entityManager.createQuery(query);
        List<VehicleModel> result = typedQuery.getResultList();

        return result.stream().map(vehicleMapper::toEntity).toList();
    }

    @Override
    public VehicleEntity findById(Long id) {
        VehicleModel model = entityManager.find(VehicleModel.class, id);
        
        if (model == null) {
            throw new VehicleNotFoundException("Vehicle not found with id: " + id);
        }
        
        return vehicleMapper.toEntity(model);
    }

    @Override
    @Transactional
    public boolean update(Long id, VehicleEntity vehicleEntity) {
        VehicleModel existingModel = entityManager.find(VehicleModel.class, id);

        if (existingModel == null) {
            return false;
        }

        vehicleMapper.updateModel(existingModel, vehicleEntity);
        entityManager.merge(existingModel);
        return true;
    }
}
