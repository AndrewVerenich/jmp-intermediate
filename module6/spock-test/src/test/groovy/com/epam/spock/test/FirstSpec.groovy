package com.epam.spock.test

import com.epam.spock.sample.service.SomeService
import spock.lang.Specification

class FirstSpec extends Specification {

    def setup() {
        println "Setup Method"
    }

    def "two plus three should be equal five"() {
        given: "two numbers"
        int left = 2
        int right = 3

        when: "add first to second"
        int result = left + right

        then: "result should be 5"
        result == 5
    }

    def "numbers to the power of two"(int a, int b, int c) {
        expect:
        Math.pow(a, b) == c

        where:
        a | b | c
        1 | 2 | 1
        2 | 2 | 4
        3 | 2 | 9
    }

    def "test stubbing"() {
        given:
        SomeService service = Mock(SomeService.class)
        service.getBool() >> true

        when: "make stub"
        service.getBool()

        then: "result should be 5"
        1 * service.getBool()
    }
}
