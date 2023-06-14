package com.numble.reservation_service.domain.customer

import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class CustomerServiceTest extends Specification {

    def mockCustomerRepository = Mock(CustomerRepository.class)
    def customerService = new CustomerService(mockCustomerRepository)

    def "사용자는 회원가입을 할 수 있다."() {
        given:
        def request = new CustomerEnrollRequest(email, password, nickname, gender, age, phone)
        def customer = request.toEntity()
        ReflectionTestUtils.setField(customer, "id", 1L)

        when:
        def enrolledId = customerService.enroll(request)

        then:
        1 * mockCustomerRepository.save(_) >> customer
        enrolledId == customer.id()

        where:
        email                || password    || nickname    || gender       || age || phone
        "example1@gmail.com" || "example1!" || "mockUser1" || GENDER.MAN   || 20  || "01020765571"
        "example2@gmail.com" || "example2!" || "mockUser2" || GENDER.WOMAN || 5   || "01020005571"
        "example3@gmail.com" || "example3!" || "mockUser3" || GENDER.MAN   || 20  || "01020765500"
    }
}
