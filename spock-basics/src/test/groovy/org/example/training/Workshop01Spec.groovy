package org.example.training

import org.junit.Assert
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.CoreMatchers.containsString
import static org.hamcrest.MatcherAssert.assertThat
/**
 * These are the first set of exercises for the Spock workshop. All you need
 * to do is write some feature methods to verify the behaviour of the methods
 * in the {@link Exercises} class. No mocking is required, but you will need
 * to handle exceptions and multiple data sets.
 */
class Workshop01Spec extends Specification {
    def exercises = new Exercises()

    @Shared def samplePeople = [
            new Person(firstName: "Joe", lastName: "Bloggs"),
            new Person(firstName: "Jill", lastName: "Dash"),
            new Person(firstName: "Arthur", lastName: "Dent"),
            new Person(firstName: "Selina", lastName: "Kyle") ]

    /**
     * <p>TODO #01: Write a feature method for the {@link Exercises#hypotenuseLength(double, double)}
     * method. Think of suitable data sets, such as what happens if one or both
     * sides are zero.</p>
     */
    @Unroll
    def "calculate hypotenuse length for sides #side1 and #side2"() {
        expect: "hypotenuse length is calculated correctly"
        Assert.assertEquals(exercises.hypotenuseLength(side1, side2,), expected, 0.001)

        where:
        side1 | side2 | expected
        0     | 3     | 3
        3     | 0     | 3
        0     | 0     | 0
        1     | 1     | 1.414D
        5     | 4     | 6.403D
    }

    /**
     * <p>TODO #02: Write a feature method for {@link Exercises#hypotenuseLength(double, double)}
     * that checks the behaviour when either argument is negative. The method
     * should throw an IllegalArgumentException in this case.</p>
     */
    @Unroll
    def "should throw an IllegalArgumentException when #side1 or #side2 is negative"() {
        when: "I calculate the hypotenuse with negatives side lengths"
        exercises.hypotenuseLength(side1, side2)

        then:
        def ex = thrown(IllegalArgumentException)
        println ex.message
        assertThat(ex.message, containsString("`${side}` cannot be negative"))

        where:
        side1 | side2
        -2    | -3
        -3    | 2
        3     | -2

        side = side1 > 0 ? "side2" : "side1"

    }

    /**
     * <p>TODO #03: Write a feature method for {@link Exercises#median(java.util.Collection)}.
     * Be sure to check the behaviour for both odd and even numbers of elements
     * as well as an empty collection.</p>
     */
    @Unroll
    def "calculate de median for number collection: #nums"() {
        expect: "The median of a numbers collections is calculated correctly"
        exercises.median(nums) == expected_result

        where:
        nums              |  expected_result
        [1]               |  1
        [1, 2 , 3]        |  2
        [2, 4, 9, -2, 0]  |  2
        [1, 1, 2, 2]      |  1.5
    }

    /**
     * <p>TODO #04: Write a feature method for {@link Exercises#fullNames(java.util.List)}.
     * You can use the {@code samplePeople} property as a source of test data.</p>
     */
    @Unroll
    def "Get a list of full names of people"() {
        expect: "A list of full names of a given Person Objects"
        exercises.fullNames(people) == expected

        where:
        people        | expected
        []            | []
        samplePeople  | ["Joe Bloggs", "Jill Dash", "Arthur Dent", "Selina Kyle"]
    }

    /**
     * <p>TODO #05: Write a feature method for {@link Exercises#createPeople(java.util.List)}.
     * This is a variation of the previous test, but the result is a collection
     * of {@link Person} objects. Note that {@code Person} is annotated with
     * {@code @Canonical}, they can be easily compared with {@code equals()}.</p>
     */
    def "Create a collection of Person objects from a list of names"(){
        given: "A list a people names"
        def peopleNames = ["Joe Bloggs", "Jill Dash", "Arthur Dent", "Selina Kyle"]

        when: "I create a people list"
        def peopleList = exercises.createPeople(peopleNames)

        then: "I get a List of people with the correct names"
        peopleList.every { it instanceof Person}
        peopleList.size() == peopleNames.size()
        peopleList.find() { it.firstName == "Jill" && it.lastName == "Dash"}
    }

    /**
     * <p>TODO #06: (Optional) Write a feature method for {@link Exercises#characterCount(java.lang.String)}.
     * The target method creates a new instance of {@code File}, so you will
     * either have to use the files in the test <em>resources</em> directory
     * or mock the creation of the {@code File} somehow.</p>
     */


}
