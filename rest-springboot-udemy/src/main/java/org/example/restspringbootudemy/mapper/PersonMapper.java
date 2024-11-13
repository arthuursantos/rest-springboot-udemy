//package org.example.restspringbootudemy.mapper;
//
//import org.example.restspringbootudemy.data.vo.v1.PersonVO;
//import org.example.restspringbootudemy.data.vo.v2.PersonVOv2;
//import org.example.restspringbootudemy.entities.Person;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PersonMapper {
//
//    public Person convertPersonVoToPerson(PersonVOv2 personVO) {
//        return Person.builder()
//                .id(personVO.getId())
//                .address(personVO.getAddress())
//                .phone(personVO.getAddress())
//                .email(personVO.getEmail())
//                .firstName(personVO.getFirstName())
//                .lastName(personVO.getLastName())
//                .build();
//    }
//
//    public PersonVOv2 convertPersonToPersonVO(Person person) {
//        return PersonVOv2.builder()
//                .id(person.getId())
//                .address(person.getAddress())
//                .phone(person.getPhone())
//                .email(person.getEmail())
//                .firstName(person.getFirstName())
//                .lastName(person.getLastName())
//                .build();
//    }
//
//}
