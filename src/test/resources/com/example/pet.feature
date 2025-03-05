Feature: Pet API Tests
Background:
  Given url baseUrl + '/pet/findByStatus?status=sold'

  Scenario: Find Sold Pets

    When method get
    Then status 200

  Scenario: List names of sold pets

    When method GET
    Then status 200
    And match response == '#[]'
    *  def pets = karate.jsonPath(response, "$[*]")
    * def soldPets = []
    * karate.forEach(pets, function(pet) { if (pet.id && pet.name) { soldPets.push('{id: ' + pet.id + ', name: "' + pet.name + '"}') } })
    * def formattedList = '[' + soldPets.join(', ') + ']'
    * print 'Lista de mascotas vendidas: ' + formattedList


  Scenario: Count Names

    When method get
    Then status 200

    * def pets = response
    * def petProcessor = Java.type('com.example.PetData')
    * def petNames = petProcessor.countPetNames(pets)


  Scenario: Sold pets with the same name

    When method get
    Then status 200
    * def soldPets = []
    * karate.forEach(response, function(pet) {if (pet.id) {if (pet.name) {soldPets.push({ id: pet.id, name: pet.name })} else {print ("Mascota sin nombre ignorada:"), pet}}})
    * def PetData = Java.type('com.example.PetData')
    * def nameCounts = PetData.countPetNames(soldPets)

    * def jsonUtils = Java.type('com.intuit.karate.JsonUtils')
    * def nameCountsString = jsonUtils.toJson(nameCounts)
    * print 'Lista de mascotas vendidas con el mismo nombre:' +nameCountsString