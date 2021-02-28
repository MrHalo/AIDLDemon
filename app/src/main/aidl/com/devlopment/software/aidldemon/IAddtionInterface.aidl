// IAddtionInterface.aidl
package com.devlopment.software.aidldemon;

// Declare any non-default types here with import statements
import com.devlopment.software.aidldemon.Person;
interface IAddtionInterface {
    int add(int val1, int val2);
    boolean addPerson(in Person person);
    List<Person> getPersonList();
}
