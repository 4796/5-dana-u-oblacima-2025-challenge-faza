package com.example.menze.dto;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class CanteenSlotsList {

	Long canteenId;
	List<CanteenSlot> slots;
}
