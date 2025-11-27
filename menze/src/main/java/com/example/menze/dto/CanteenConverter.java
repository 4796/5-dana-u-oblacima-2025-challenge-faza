package com.example.menze.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.menze.model.Canteen;
import com.example.menze.model.Meal;
import com.example.menze.model.WorkingHours;

@Component
public class CanteenConverter {
	public Canteen toEntity(CanteenDTO request) {
        if (request == null) {
            return null;
        }

        Canteen canteen = new Canteen();
        canteen.setName(request.getName());
        canteen.setLocation(request.getLocation());
        canteen.setCapacity(request.getCapacity());

        Map<Meal, WorkingHours> workingHoursMap = new EnumMap<>(Meal.class);
        if (request.getWorkingHours() != null) {
            for (WorkingHoursDto dto : request.getWorkingHours()) {
                if (dto.getMeal() == null) {
                    continue; 
                }
                WorkingHours wh = new WorkingHours();
                wh.setFrom(dto.getFrom());
                wh.setTo(dto.getTo());
                workingHoursMap.put(dto.getMeal(), wh);
            }
        }
        canteen.setWorkingHours(workingHoursMap);

        return canteen;
    }
	
	
	
	public CanteenDTO toDto(Canteen canteen) {
        if (canteen == null) {
            return null;
        }

        CanteenDTO dto = new CanteenDTO();
        dto.setId(canteen.getId());
        dto.setName(canteen.getName());
        dto.setLocation(canteen.getLocation());
        dto.setCapacity(canteen.getCapacity());

        
        if (canteen.getWorkingHours() != null) {
        	List<WorkingHoursDto> workingHoursList = new ArrayList<>();
            for (Map.Entry<Meal, WorkingHours> entry : canteen.getWorkingHours().entrySet()) {
                WorkingHoursDto whDto = new WorkingHoursDto();
                whDto.setMeal(entry.getKey());
                whDto.setFrom(entry.getValue().getFrom());
                whDto.setTo(entry.getValue().getTo());
                workingHoursList.add(whDto);
            }
            workingHoursList.sort(Comparator.comparingInt(w -> w.getMeal().ordinal()));
            dto.setWorkingHours(workingHoursList);
        }
       

        return dto;
    }
	
	
	
	public void updateEntity(Canteen canteen, UpdateCanteenRequest request) {
        if (canteen == null || request == null) {
            return;
        }
        if(request.getName()!=null && request.getName().length()>0)
        	canteen.setName(request.getName());
        if(request.getLocation()!=null && request.getLocation().length()>0)
        	canteen.setLocation(request.getLocation());
        if(request.getCapacity()!=null)
        	canteen.setCapacity(request.getCapacity());
        
        
        if (request.getWorkingHours() != null) {
        	Map<Meal, WorkingHours> workingHoursMap = new EnumMap<>(Meal.class);
            for (WorkingHoursDto dto : request.getWorkingHours()) {
                if (dto.getMeal() == null) {
                    continue;
                }
                WorkingHours wh = new WorkingHours();
                wh.setFrom(dto.getFrom());
                wh.setTo(dto.getTo());
                workingHoursMap.put(dto.getMeal(), wh);
            }
            
            canteen.setWorkingHours(workingHoursMap);
        }
        
    }
}