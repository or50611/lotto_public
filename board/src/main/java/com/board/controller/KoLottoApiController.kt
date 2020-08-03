package com.board.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class KoLottoApiController {
	
	@RequestMapping("/hellokotlin")
	fun methodTest (){
		System.out.println("Hello Kotlin!!");
	}
}