package com.servicios.reformas.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ControllerUtill {
	private static final Logger log = LoggerFactory.getLogger(ControllerUtill.class);

	/**
	 * 
	 * @param bindingResult
	 * @return
	 */
	static Map<String, String> obtenerErrores(BindingResult bindingResult){
		Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
				fieldError -> fieldError.getField() + "Error", 
				FieldError::getDefaultMessage
				);
				
		return bindingResult.getFieldErrors().stream().collect(collector);
	}
	
	/**
	 * @param page
	 * @return paginacion computada
	 */
	static int[] computePagination(Page page) {
        Integer totalPages = page.getTotalPages();
        log.info("IN computePagination():", totalPages.toString());
        if (totalPages > 4) {
            Integer pageNumber = page.getNumber() + 1;
            Integer[] head = pageNumber > 4 ? new Integer[]{1, -1} : new Integer[]{1, 2, 3};
            Integer[] tail = pageNumber < (totalPages - 3) ? new Integer[]{-1, totalPages} : new Integer[]{totalPages - 2, totalPages - 1, totalPages};
            Integer[] bodyBefore = (pageNumber > 4 && pageNumber < (totalPages - 1)) ? new Integer[]{pageNumber - 2, pageNumber - 1} : new Integer[]{};
            Integer[] bodyAfter = (pageNumber > 2 && pageNumber < (totalPages - 3)) ? new Integer[]{pageNumber + 1, pageNumber + 2} : new Integer[]{};

            List<Integer> list = new ArrayList<>();
            Collections.addAll(list, head);
            Collections.addAll(list, bodyBefore);
            Collections.addAll(list, (pageNumber > 3 && pageNumber < totalPages - 2) ? new Integer[]{pageNumber} : new Integer[]{});
            Collections.addAll(list, bodyAfter);
            Collections.addAll(list, tail);
            Integer[] arr = list.toArray(new Integer[0]);
            int[] res = Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
            return res;
        } else {
            return IntStream.rangeClosed(1, totalPages).toArray();
        }
    }
	 

}
