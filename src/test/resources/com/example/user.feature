
Feature: User API Tests

  Scenario: Create User
    Given url baseUrl + '/user'
    And request { username: 'testUser', firstName: 'John', lastName: 'Doe', email: 'johndoe@example.com', password: 'test123', phone: '1234567890', userStatus: 1 }
    When method post
    Then status 200

  Scenario: Retrieve User
    Given url baseUrl + '/user/testUser'
    And retry until responseStatus == 200
    When method get
    Then status 200
    And match response.username == 'testUser'