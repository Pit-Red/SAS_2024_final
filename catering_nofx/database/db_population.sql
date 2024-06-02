-- Inserting data for table `Roles`
INSERT INTO `Roles` (`id`, `role`)
VALUES ('c', 'cuoco'),
       ('h', 'chef'),
       ('o', 'organizzatore'),
       ('s', 'servizio');


-- Inserting data for table `Users`
INSERT INTO `Users` (`id`, `username`)
VALUES (1, 'Alice'),
       (2, 'Bob'),
       (3, 'Charlie'),
       (4, 'Dana'),
       (5, 'Eva'),
       (6, 'Frank'),
       (7, 'Georgia'),
       (8, 'Henry'),
       (9, 'Isla'),
       (10, 'Jack');

-- Inserting data for table `UserRoles`
INSERT INTO `UserRoles` (`user_id`, `role_id`)
VALUES (1, 'o'),
       (2, 'h'),
       (3, 's'),
       (4, 'c'),
       (5, 'h'), -- Eva as Chef
       (6, 'h'), -- Frank as Head Chef
       (7, 'o'), -- Georgia as Organizer
       (8, 's'), -- Henry as Service Staff
       (9, 'c'), -- Isla as Chef
       (10, 'h'); -- Jack as Head Chef

-- Inserting data for table `Events`
INSERT INTO `Events` (`id`, `name`, `date_start`, `date_end`, `expected_participants`, `organizer_id`)
VALUES (1, 'Wedding Reception', '2023-07-15', '2023-07-15', 200, 2),
       (2, 'Corporate Gala', '2023-08-01', '2023-08-01', 300, 3),
       (3, 'Birthday Party', '2023-09-20', '2023-09-20', 100, 1);



-- Inserting data for table `Preparations`
INSERT INTO `Preparations` (`id`)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8);

-- Inserting data for table `Recipes`
INSERT INTO `Recipes` (`id`)
VALUES (1),
       (2),
       (3);


-- Inserting data for table `RecipePreparations`
INSERT INTO `RecipePreparations` (`recipe_id`, `preparation_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 2),
       (3, 7),
       (3, 8);

-- Inserting data for table `CookingProcedures`
INSERT INTO `CookingProcedures` (`id`, `name`, `type`, `fk_referenced_recipe`, `fk_referenced_preparation`)
VALUES (1, 'Pasta', 'recipe', 1, NULL),
       (2, 'Pasta Dough', 'preparation', NULL, 1),
       (3, 'Tomato Sauce', 'preparation', NULL, 2),
       (4, 'Boil Water', 'preparation', NULL, 3),
       (5, 'Curry Chicken', 'recipe', 2, NULL),
       (6, 'Marinate Raw Chicken', 'preparation', NULL, 4),
       (7, 'Grill Chicken', 'preparation', NULL, 5),
       (8, 'Curry Sauce', 'preparation', NULL, 6),
       (9, 'Lasagna', 'recipe', 3, NULL),
       (10, 'Besciamella', 'preparation', NULL, 7),
       (11, 'Bake in the oven', 'preparation', NULL, 8);

-- Inserting data for table `Menus`
INSERT INTO `Menus` (`id`, `title`, `owner_id`, `published`)
VALUES (1, 'Spring Wedding Menu', 5, 1),
       (2, 'Corporate Dinner', 3, 1),
       (3, 'Birthday Desserts', 2, 1);

-- Inserting data for table `MenuSections`
INSERT INTO `MenuSections` (`id`, `menu_id`, `name`, `position`)
VALUES (1, 1, 'Main Course', 1),
       (2, 1, 'Proteins', 2),
       (3, 2, 'Main Course', 3);

-- Inserting data for table `MenuItems`
INSERT INTO `MenuItems` (`id`, `menu_id`, `section_id`, `description`, `recipe_id`, `position`)
VALUES (1, 1, 1, 'Homemade Pasta with Tomato Sauce', 1, 1),
       (2, 1, 2, 'Chicken Curry', 2, 2),
       (3, 2, 3, 'Lasagna', 3, 1);

-- Inserting data for table `MenuFeatures`
INSERT INTO `MenuFeatures` (`menu_id`, `name`, `value`)
VALUES (1, 'Normal', 1),
       (2, 'Gluten Free', 1);

-- Inserting data for table `Tasks`
/*INSERT INTO `Tasks` (`id`, `cooking_procedure_id`, `cook_id`, `time_to_complete`, `completed`, `to_prepare`)
VALUES (1, 5, 9, 'PT2H', FALSE, TRUE),
       (2, 4, 10, 'PT1H30M', FALSE, TRUE),
       (3, 6, 5, 'PT1H', TRUE, TRUE);*/

-- Inserting data for table `SummarySheets`
INSERT INTO `SummarySheets` (`id`)
VALUES (1), (2), (3);

-- Inserting data for table `ListedTasks`
INSERT INTO `ListedTasks` (`summary_sheet_id`, `task_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3);

-- Inserting data for table `Services`
INSERT INTO `Services` (`id`, `event_id`, `name`, `used_menu_id`, `service_date`, `time_start`, `time_end`, `expected_participants`, `chef_id`, `summary_sheet_id`)
VALUES (1, 1, 'Lunch Service', 1, '2023-07-15', '12:00:00', '15:00:00', 200, 5, 1),
       (2, 2, 'Dinner Service', 2, '2023-08-01', '18:00:00', '21:00:00', 300, 2, 2);

-- Inserting data for table `ListedProcedures`
INSERT INTO `ListedProcedures` (`summary_sheet_id`, `procedure_id`, `position`)
VALUES
    (1, 1, 0),  -- Boil Pasta at position 0 in Summary Sheet 1
    (1, 2, 1),  -- Prepare Sauce at position 1 in Summary Sheet 1
    (1, 5, 2),  -- Grill Chicken at position 2 in Summary Sheet 1
    (1, 4, 3),  -- Mix Salad at position 3 in Summary Sheet 1

    (2, 3, 0),  -- Bake Bread at position 0 in Summary Sheet 2
    (2, 6, 1),  -- Fry Potatoes at position 1 in Summary Sheet 2
    (2, 7, 2),  -- Steam Vegetables at position 2 in Summary Sheet 2

    (3, 8, 0),  -- Chop Onions at position 0 in Summary Sheet 3
    (3, 9, 1),  -- Prepare Dough at position 1 in Summary Sheet 3
    (3, 10, 2); -- Marinate Beef at position 2 in Summary Sheet 3
