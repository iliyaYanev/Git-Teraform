package com.mentormate.sales.common.command;


import com.mentormate.sales.common.value.Money;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveCreditCommand implements Command {
	private Long orderId;
	private Money orderTotal;
	private long customerId;
}
