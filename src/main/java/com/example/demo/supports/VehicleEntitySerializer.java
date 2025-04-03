package com.example.demo.supports;

import com.example.demo.entities.VehicleEntity;
import com.example.demo.properties.VehicleProperties;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class VehicleEntitySerializer {

    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static Map<String, Object> serialize(VehicleEntity vehicle) {
        Map<String, Object> serialized = new LinkedHashMap<>();
        VehicleProperties props = vehicle.getProperties();
        
        vehicle.getId().ifPresent(id -> serialized.put("id", id));
        
        serialized.put("model", props.getModel());
        serialized.put("brand", props.getBrand());
        serialized.put("year", props.getYear());
        
        if (props.getDescription() != null) {
            serialized.put("description", props.getDescription());
        }
        
        serialized.put("sold", props.isSold());
        
        if (props.getCreated() != null) {
            serialized.put("created", DATE_FORMATTER.format(props.getCreated()));
        }
        if (props.getUpdated() != null) {
            serialized.put("updated", DATE_FORMATTER.format(props.getUpdated()));
        }
        
        return serialized;
    }

    public static Map<String, Object> serializeWithDetails(VehicleEntity vehicle) {
        Map<String, Object> serialized = serialize(vehicle);
        
        serialized.put("status", vehicle.getProperties().isSold() ? "sold" : "available");
        
        return serialized;
    }
}