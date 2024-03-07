package com.ict.teamProject.schedule.service;

import java.util.List;
import java.util.Map;

public interface SCHService<T> {

	void insert(SCHDto schedule);

	SCHDto seleteOne(String string);

	List<SCHDto> seleteAll(String string);

	List<SCHDto> seleteTodayAll(String string);

	void delete(Map<String, Object> map);
}
