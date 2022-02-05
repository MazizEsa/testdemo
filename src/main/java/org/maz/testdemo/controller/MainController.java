package org.maz.testdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	@GetMapping("/")
	ResponseEntity<String> callRoot(final @RequestParam(required = false) String param) {
		if (ObjectUtils.isEmpty(param)) {
			return ResponseEntity.ok(StaticMethod.returnStaticValue());
		}
		return ResponseEntity.ok(StaticMethod.buildValue(param));
	}
}
