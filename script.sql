--
-- PostgreSQL database dump
--

-- Dumped from database version 14.13 (Ubuntu 14.13-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.13 (Ubuntu 14.13-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    identification bigint NOT NULL,
    version bigint NOT NULL,
    phone character varying(255) NOT NULL,
    address character varying(255),
    preferences character varying(255),
    username character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    active character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    email character varying(255) NOT NULL
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: novelty_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.novelty_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.novelty_id_seq OWNER TO postgres;

--
-- Name: novelty; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.novelty (
    id bigint DEFAULT nextval('public.novelty_id_seq'::regclass) NOT NULL,
    id_order bigint,
    novelty_type text,
    novelty_description text,
    create_at timestamp without time zone
);


ALTER TABLE public.novelty OWNER TO postgres;

--
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_id_seq OWNER TO postgres;

--
-- Name: orderp; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderp (
    id bigint DEFAULT nextval('public.order_id_seq'::regclass) NOT NULL,
    order_description text,
    id_client bigint,
    order_status text,
    discount numeric,
    subtotal numeric,
    total numeric,
    create_at timestamp without time zone,
    update_at timestamp without time zone
);


ALTER TABLE public.orderp OWNER TO postgres;

--
-- Name: permission_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permission_id_seq OWNER TO postgres;

--
-- Name: permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permission (
    id bigint DEFAULT nextval('public.permission_id_seq'::regclass) NOT NULL,
    name text,
    permission_description text,
    module text
);


ALTER TABLE public.permission OWNER TO postgres;

--
-- Name: permission_role_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.permission_role_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permission_role_role_id_seq OWNER TO postgres;

--
-- Name: permission_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permission_role (
    role_id bigint DEFAULT nextval('public.permission_role_role_id_seq'::regclass) NOT NULL,
    permission_id bigint
);


ALTER TABLE public.permission_role OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO postgres;

--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id bigint DEFAULT nextval('public.product_id_seq'::regclass) NOT NULL,
    name text,
    product_description text,
    price numeric,
    stock bigint,
    image bytea,
    create_at timestamp without time zone,
    update_at timestamp without time zone
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: product_order_product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_order_product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_order_product_id_seq OWNER TO postgres;

--
-- Name: product_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_order (
    product_id bigint DEFAULT nextval('public.product_order_product_id_seq'::regclass) NOT NULL,
    order_id bigint NOT NULL
);


ALTER TABLE public.product_order OWNER TO postgres;

--
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    version bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_seq OWNER TO postgres;

--
-- Name: role_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role_user (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.role_user OWNER TO postgres;

--
-- Name: role_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_user_user_id_seq OWNER TO postgres;

--
-- Name: t_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_order (
    id bigint DEFAULT nextval('public.order_id_seq'::regclass) NOT NULL,
    order_description text,
    id_client bigint,
    order_status text,
    discount numeric,
    subtotal numeric,
    total numeric,
    create_at timestamp without time zone DEFAULT now(),
    update_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.t_order OWNER TO postgres;

--
-- Name: t_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_user (
    identification bigint NOT NULL,
    phone character varying(255),
    address character varying(255),
    update_at timestamp without time zone,
    username character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    lastname character varying(255),
    create_at timestamp without time zone,
    email character varying(255) NOT NULL,
    preferences character varying(255),
    version integer DEFAULT 0,
    active boolean DEFAULT true
);


ALTER TABLE public.t_user OWNER TO postgres;

--
-- Name: t_user_identification_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.t_user_identification_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_user_identification_seq OWNER TO postgres;

--
-- Name: user_identification_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_identification_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_identification_seq OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    identification bigint DEFAULT nextval('public.user_identification_seq'::regclass) NOT NULL,
    name text NOT NULL,
    lastname text,
    username text,
    phone text,
    password text,
    address text,
    email text,
    create_at timestamp without time zone,
    update_at timestamp without time zone
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (identification);


--
-- Name: novelty novelty_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.novelty
    ADD CONSTRAINT novelty_pkey PRIMARY KEY (id);


--
-- Name: orderp orderp_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderp
    ADD CONSTRAINT orderp_pkey PRIMARY KEY (id);


--
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- Name: permission_role permission_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission_role
    ADD CONSTRAINT permission_role_pkey PRIMARY KEY (role_id);


--
-- Name: product_order product_order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_order
    ADD CONSTRAINT product_order_pkey PRIMARY KEY (product_id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: role_user role_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_user
    ADD CONSTRAINT role_user_pkey PRIMARY KEY (role_id, user_id);


--
-- Name: t_order t_order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_order
    ADD CONSTRAINT t_order_pkey PRIMARY KEY (id);


--
-- Name: t_user t_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_user
    ADD CONSTRAINT t_user_pkey PRIMARY KEY (identification);


--
-- Name: customer uk_dwk6cx0afu8bs9o4t536v1j5v; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT uk_dwk6cx0afu8bs9o4t536v1j5v UNIQUE (email);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (identification);


--
-- Name: role_user fk2hqgmwt3nu6jb9sgbe0tv0cu3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_user
    ADD CONSTRAINT fk2hqgmwt3nu6jb9sgbe0tv0cu3 FOREIGN KEY (user_id) REFERENCES public.customer(identification);


--
-- Name: role_user fkiqpmjd2qb4rdkej916ymonic6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_user
    ADD CONSTRAINT fkiqpmjd2qb4rdkej916ymonic6 FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- Name: role_user fkj52m9a8dklvuc7aaan1cbsue6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_user
    ADD CONSTRAINT fkj52m9a8dklvuc7aaan1cbsue6 FOREIGN KEY (user_id) REFERENCES public.t_user(identification);


--
-- Name: novelty novelty_id_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.novelty
    ADD CONSTRAINT novelty_id_order_fk FOREIGN KEY (id_order) REFERENCES public.orderp(id);


--
-- Name: orderp order_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderp
    ADD CONSTRAINT order_id_fk FOREIGN KEY (id_client) REFERENCES public."user"(identification);


--
-- Name: product_order product_order_order_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_order
    ADD CONSTRAINT product_order_order_id_fk FOREIGN KEY (order_id) REFERENCES public.orderp(id);


--
-- Name: product_order product_order_product_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_order
    ADD CONSTRAINT product_order_product_id_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: permission_role role_permission_permission_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission_role
    ADD CONSTRAINT role_permission_permission_id_fk FOREIGN KEY (permission_id) REFERENCES public.permission(id);


--
-- PostgreSQL database dump complete
--

