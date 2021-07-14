package com.momo.server.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.momo.server.domain.Meet;
import com.momo.server.domain.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTimeUpdateRequestDto {
	
	
	private String meetId;
    private LocalDate date;
    private String timeslot;
	private boolean possible;

	public UserTimeUpdateRequestDto(String meetId, LocalDate date, String timeslot, boolean possible) {
		this.meetId = meetId;
		this.date = date;
		this.timeslot = timeslot;
		this.possible = possible;
	}
	
}
