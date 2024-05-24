CREATE TABLE accindents_rules (
                                 id serial PRIMARY KEY,
                                 accindent_id int not null REFERENCES accidents(id),
                                 rule_id int not null REFERENCES rules(id),
                                 UNIQUE (accindent_id, rule_id)
);