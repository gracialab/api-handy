CREATE SEQUENCE IF NOT EXISTS novelty_id_seq;
CREATE SEQUENCE IF NOT EXISTS order_id_seq;
CREATE SEQUENCE IF NOT EXISTS permission_id_seq;
CREATE SEQUENCE IF NOT EXISTS product_id_seq;
CREATE SEQUENCE IF NOT EXISTS role_id_seq;
CREATE SEQUENCE IF NOT EXISTS t_user_identification_seq;

CREATE TABLE IF NOT EXISTS "t_user" (
    identification bigint NOT NULL PRIMARY KEY DEFAULT nextval('t_user_identification_seq'),
    name text NOT NULL,
    lastname text,
    username text,
    phone text,
    password text,
    address text,
    email text,
    create_at timestamp DEFAULT now(),
    update_at timestamp DEFAULT now()
    );

CREATE TABLE IF NOT EXISTS role (
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('role_id_seq'),
    name text,
    description text
    );

CREATE TABLE IF NOT EXISTS permission (
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('permission_id_seq'),
    name text,
    description text,
    module text
    );

CREATE TABLE IF NOT EXISTS "t_order" (
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('order_id_seq'),
    order_description text,
    id_client bigint,
    order_status text,
    discount decimal,
    subtotal decimal,
    total decimal,
    create_at timestamp DEFAULT now(),
    update_at timestamp DEFAULT now(),
    CONSTRAINT fk_order_t_user FOREIGN KEY (id_client) REFERENCES "t_user" (identification) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS novelty (
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('novelty_id_seq'),
    id_order bigint,
    novelty_type text,
    novelty_description text,
    create_at timestamp DEFAULT now(),
    CONSTRAINT fk_novelty_order FOREIGN KEY (id_order) REFERENCES "t_order" (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS product (
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('product_id_seq'),
    name text,
    product_description text,
    price decimal,
    stock bigint,
    image bytea,
    create_at timestamp DEFAULT now(),
    update_at timestamp DEFAULT now()
    );

CREATE TABLE IF NOT EXISTS product_order (
    product_id bigint NOT NULL,
    order_id bigint NOT NULL,
    PRIMARY KEY (product_id, order_id),
    CONSTRAINT fk_product_order_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT fk_product_order_order FOREIGN KEY (order_id) REFERENCES "t_order" (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS permission_role (
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_permission_role_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE,
    CONSTRAINT fk_permission_role_permission FOREIGN KEY (permission_id) REFERENCES permission (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS role_user (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_role_user_t_user FOREIGN KEY (user_id) REFERENCES "t_user" (identification) ON DELETE CASCADE,
    CONSTRAINT fk_role_user_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
    );
