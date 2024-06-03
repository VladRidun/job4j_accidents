CREATE TABLE accidents_rules (
                                 id serial PRIMARY KEY,
                                 accident_id int not null REFERENCES accidents(id),
                                 rule_id int not null REFERENCES rules(id),
                                 UNIQUE (accident_id, rule_id)
);