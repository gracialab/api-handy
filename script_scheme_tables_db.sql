CREATE SEQUENCE IF NOT EXISTS novelty_id_seq;
CREATE SEQUENCE IF NOT EXISTS order_id_seq;
CREATE SEQUENCE IF NOT EXISTS permission_id_seq;
CREATE SEQUENCE IF NOT EXISTS permission_role_role_id_seq;
CREATE SEQUENCE IF NOT EXISTS product_id_seq;
CREATE SEQUENCE IF NOT EXISTS product_order_product_id_seq;
CREATE SEQUENCE IF NOT EXISTS role_id_seq;
CREATE SEQUENCE IF NOT EXISTS role_user_user_id_seq;
CREATE SEQUENCE IF NOT EXISTS user_identification_seq;

CREATE TABLE IF NOT EXISTS novelty (
                                       id bigint NOT NULL PRIMARY KEY DEFAULT nextval('novelty_id_seq'),
    id_order bigint,
    novelty_type text,
    novelty_description text,
    create_at timestamp
    );

CREATE TABLE IF NOT EXISTS "orderp" (
                                        id bigint NOT NULL PRIMARY KEY DEFAULT nextval('order_id_seq'),
    order_description text,
    id_client bigint,
    order_status text,
    discount decimal,
    subtotal decimal,
    total decimal,
    create_at timestamp,
    update_at timestamp
    );

CREATE TABLE IF NOT EXISTS permission (
                                          id bigint NOT NULL PRIMARY KEY DEFAULT nextval('permission_id_seq'),
    name text,
    permission_description text,
    module text
    );

CREATE TABLE IF NOT EXISTS permission_role (
                                               role_id bigint NOT NULL PRIMARY KEY DEFAULT nextval('permission_role_role_id_seq'),
    permission_id bigint
    );

CREATE TABLE IF NOT EXISTS product (
                                       id bigint NOT NULL PRIMARY KEY DEFAULT nextval('product_id_seq'),
    name text,
    product_description text,
    price decimal,
    stock bigint,
    image bytea,
    create_at timestamp,
    update_at timestamp
    );

CREATE TABLE IF NOT EXISTS product_order (
                                             product_id bigint NOT NULL PRIMARY KEY DEFAULT nextval('product_order_product_id_seq'),
    order_id bigint NOT NULL
    );

CREATE TABLE IF NOT EXISTS role (
                                    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('role_id_seq'),
    name text,
    role_description text
    );

CREATE TABLE IF NOT EXISTS role_user (
                                         user_id bigint NOT NULL PRIMARY KEY DEFAULT nextval('role_user_user_id_seq'),
    role_id bigint
    );

CREATE TABLE IF NOT EXISTS "user" (
                                      identification bigint NOT NULL PRIMARY KEY DEFAULT nextval('user_identification_seq'),
    name text NOT NULL,
    lastname text,
    username text,
    phone text,
    password text,
    address text,
    email text,
    create_at timestamp,
    update_at timestamp
    );

ALTER TABLE role ADD CONSTRAINT role_id_fk FOREIGN KEY (id) REFERENCES permission_role (role_id);
ALTER TABLE permission_role ADD CONSTRAINT role_permission_permission_id_fk FOREIGN KEY (permission_id) REFERENCES permission (id);
ALTER TABLE "user" ADD CONSTRAINT user_identification_fk FOREIGN KEY (identification) REFERENCES role_user (user_id);
ALTER TABLE role_user ADD CONSTRAINT user_role_role_id_fk FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE "orderp" ADD CONSTRAINT order_id_fk FOREIGN KEY (id_client) REFERENCES "user" (identification);
ALTER TABLE novelty ADD CONSTRAINT novelty_id_order_fk FOREIGN KEY (id_order) REFERENCES "orderp" (id);
ALTER TABLE product_order ADD CONSTRAINT product_order_order_id_fk FOREIGN KEY (order_id) REFERENCES "orderp" (id);
ALTER TABLE product_order ADD CONSTRAINT product_order_product_id_fk FOREIGN KEY (product_id) REFERENCES "product" (id);