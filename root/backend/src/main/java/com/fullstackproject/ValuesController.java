package com.fullstackproject;

import java.util.List;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/values")
public class ValuesController {
	@GetMapping()
	public ResponseEntity<List<String>> getNames() {
		List<String> names = new ArrayList<String>();
		names.add("First");
		names.add("Second");
		names.add("Third");
		return ResponseEntity.ok(names);
	}
}
