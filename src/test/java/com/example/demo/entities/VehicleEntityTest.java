package com.example.demo.entities;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.RestoreVehicleDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class VehicleEntityTest {

    @Test
    void shouldReturnCorrectIdWhenIdentityIsProvided() {
        RestoreVehicleDTO restoreDto = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        VehicleEntity entity = VehicleEntity.restore(1L, restoreDto);

        Optional<Long> id = entity.getId();

        assertThat(id).isPresent().contains(1L);
    }

    @Test
    void shouldReturnEmptyOptionalWhenIdentityIsNotProvided() {
        CreateVehicleDTO createDto = new CreateVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false
        );
        VehicleEntity entity = VehicleEntity.create(createDto);

        Optional<Long> id = entity.getId();

        assertThat(id).isEmpty();
    }

    @Test
    void shouldReturnCorrectProperties() {
        CreateVehicleDTO createDto = new CreateVehicleDTO(
            "Model 3", "Tesla", 2022, "Compact electric", false
        );

        VehicleEntity entity = VehicleEntity.create(createDto);

        assertThat(entity.getProperties().getModel()).isEqualTo("Model 3");
        assertThat(entity.getProperties().getBrand()).isEqualTo("Tesla");
        assertThat(entity.getProperties().getYear()).isEqualTo(2022);
        assertThat(entity.getProperties().getDescription()).isEqualTo("Compact electric");
        assertThat(entity.getProperties().isSold()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenComparingEntitiesWithSameId() {
        RestoreVehicleDTO dto1 = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        RestoreVehicleDTO dto2 = new RestoreVehicleDTO(
            "Model X", "Tesla", 2022, "SUV", true, null, null
        );
        VehicleEntity entity1 = VehicleEntity.restore(1L, dto1);
        VehicleEntity entity2 = VehicleEntity.restore(1L, dto2);

        assertThat(entity1.equals(entity2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenComparingEntitiesWithDifferentIds() {
        RestoreVehicleDTO dto = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        VehicleEntity entity1 = VehicleEntity.restore(1L, dto);
        VehicleEntity entity2 = VehicleEntity.restore(2L, dto);

        assertThat(entity1.equals(entity2)).isFalse();
    }

    @Test
    void shouldReturnFalseWhenComparingWithNull() {
        RestoreVehicleDTO dto = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        VehicleEntity entity = VehicleEntity.restore(1L, dto);

        assertThat(entity.equals(null)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenComparingSameInstance() {
        RestoreVehicleDTO dto = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        VehicleEntity entity = VehicleEntity.restore(1L, dto);

        assertThat(entity.equals(entity)).isTrue();
    }

    @Test
    void markAsSold_ShouldUpdateSoldStatusAndUpdatedTimestamp() {
        LocalDateTime created = LocalDateTime.now().minusDays(1);
        RestoreVehicleDTO dto = new RestoreVehicleDTO(
            "Model Y", "Tesla", 2023, "Crossover", false, created, null
        );
        VehicleEntity entity = VehicleEntity.restore(1L, dto);

        VehicleEntity soldEntity = entity.markAsSold();

        assertThat(soldEntity.getProperties().isSold()).isTrue();
        assertThat(soldEntity.getProperties().getUpdated())
            .isAfter(created)
            .isNotNull();
    }

    @Test
    void updateDescription_ShouldUpdateDescriptionAndUpdatedTimestamp() {
        LocalDateTime created = LocalDateTime.now().minusDays(1);
        RestoreVehicleDTO dto = new RestoreVehicleDTO(
            "Model Y", "Tesla", 2023, "Old description", false, created, null
        );
        VehicleEntity entity = VehicleEntity.restore(1L, dto);

        VehicleEntity updatedEntity = entity.updateDescription("New description");

        assertThat(updatedEntity.getProperties().getDescription()).isEqualTo("New description");
        assertThat(updatedEntity.getProperties().getUpdated())
            .isAfter(created)
            .isNotNull();
    }

    @Test
    void withId_ShouldCreateNewInstanceWithGivenId() {
        CreateVehicleDTO createDto = new CreateVehicleDTO(
            "Model X", "Tesla", 2023, "Electric SUV", false
        );
        VehicleEntity entity = VehicleEntity.create(createDto);

        VehicleEntity withId = entity.withId(1L);

        assertThat(withId.getId()).isPresent().contains(1L);
        assertThat(withId.getProperties()).isEqualTo(entity.getProperties());
    }

    @Test
    void hashCode_ShouldBeEqualForSameId() {
        RestoreVehicleDTO dto1 = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        RestoreVehicleDTO dto2 = new RestoreVehicleDTO(
            "Model X", "Tesla", 2022, "SUV", true, null, null
        );
        VehicleEntity entity1 = VehicleEntity.restore(1L, dto1);
        VehicleEntity entity2 = VehicleEntity.restore(1L, dto2);

        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
    }

    @Test
    void create_ShouldThrowExceptionWhenDtoIsNull() {
        assertThatThrownBy(() -> VehicleEntity.create(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("DTO cannot be null");
    }

    @Test
    void restore_ShouldThrowExceptionWhenDtoIsNull() {
        assertThatThrownBy(() -> VehicleEntity.restore(1L, null))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("Restore DTO cannot be null");
    }
}