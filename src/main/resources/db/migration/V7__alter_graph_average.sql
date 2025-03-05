ALTER TABLE graph_average
    DROP COLUMN male_exercise;

ALTER TABLE graph_average
    DROP COLUMN female_exercise;

 ALTER TABLE graph_average
    DROP COLUMN male_calorie;

ALTER TABLE graph_average
    DROP COLUMN female_calorie;

 ALTER TABLE graph_average
    ADD COLUMN exercise_avg float;

ALTER TABLE graph_average
    ADD COLUMN calorie_avg float;