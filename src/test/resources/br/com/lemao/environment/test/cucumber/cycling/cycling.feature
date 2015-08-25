Feature: Cycling

  Scenario: Biker and Bicycle
    Given I have one male biker and one bicycle for this biker
    When I read the attributes of the biker
    And I read the attributes of the bicycle
    Then the name of the biker is "Lemão"
    And the gender of the biker is "MALE"
    And the model name of the bicycle is "S-WORKS EPIC 29"
    And the serial number of the bicycle is 165487
    And the name of the owner of the bicycle is "Lemão"