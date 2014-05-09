--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE plato_dev;
--
-- Name: plato_dev; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE plato_dev WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';


ALTER DATABASE plato_dev OWNER TO postgres;

\connect plato_dev

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plato_dev; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE plato_dev IS 'platform development database';


--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: app_user_accounts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_user_accounts (
    id bigint NOT NULL,
    user_profile_id bigint,
    login_name character(50),
    login_password character varying(100),
    create_date date,
    locked_date date,
    validate_date date,
    is_account_expired boolean DEFAULT false NOT NULL,
    is_credential_expired boolean DEFAULT false NOT NULL,
    is_account_valid boolean DEFAULT false NOT NULL,
    is_account_locked boolean DEFAULT true NOT NULL,
    account_expired_date date,
    credential_expired_date date
);


ALTER TABLE public.app_user_accounts OWNER TO postgres;

--
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_id_seq OWNER TO postgres;

--
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE account_id_seq OWNED BY app_user_accounts.id;


--
-- Name: app_user_address; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_user_address (
    id bigint NOT NULL,
    street1 character(50),
    street2 character(50),
    city character(50),
    state character(2),
    county character(50),
    country character(50),
    zip character(5),
    type_id bigint,
    user_profile_id bigint NOT NULL,
    "isPrimary" boolean DEFAULT false NOT NULL
);


ALTER TABLE public.app_user_address OWNER TO postgres;

--
-- Name: address_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.address_id_seq OWNER TO postgres;

--
-- Name: address_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE address_id_seq OWNED BY app_user_address.id;


--
-- Name: app_group_auth; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_group_auth (
    id bigint NOT NULL,
    role_id bigint,
    group_id bigint
);


ALTER TABLE public.app_group_auth OWNER TO postgres;

--
-- Name: TABLE app_group_auth; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE app_group_auth IS 'account roles';


--
-- Name: app_group_members; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_group_members (
    id bigint NOT NULL,
    group_id bigint,
    user_account_id bigint
);


ALTER TABLE public.app_group_members OWNER TO postgres;

--
-- Name: app_organization_groups; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_organization_groups (
    id bigint NOT NULL,
    name character varying(100),
    description character varying(200),
    org_id bigint NOT NULL
);


ALTER TABLE public.app_organization_groups OWNER TO postgres;

--
-- Name: app_sys_address_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_sys_address_types (
    id bigint NOT NULL,
    name character varying(50),
    description character varying(200)
);


ALTER TABLE public.app_sys_address_types OWNER TO postgres;

--
-- Name: app_sys_roles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_sys_roles (
    id bigint NOT NULL,
    description character varying(200),
    name character varying(100)
);


ALTER TABLE public.app_sys_roles OWNER TO postgres;

--
-- Name: app_user_organization; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_user_organization (
    id bigint NOT NULL,
    name character varying(150),
    phone_number character varying(25),
    fax_number character varying(25),
    street1 character varying(200),
    street2 character varying(200),
    city character varying(200),
    state character(2),
    zip character(5),
    county character varying(100),
    country character varying(200),
    email_address character varying(200)
);


ALTER TABLE public.app_user_organization OWNER TO postgres;

--
-- Name: COLUMN app_user_organization.street1; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN app_user_organization.street1 IS 'address::stree1';


--
-- Name: COLUMN app_user_organization.street2; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN app_user_organization.street2 IS 'address::street2';


--
-- Name: COLUMN app_user_organization.city; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN app_user_organization.city IS 'address::city';


--
-- Name: COLUMN app_user_organization.state; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN app_user_organization.state IS 'address:state';


--
-- Name: COLUMN app_user_organization.zip; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN app_user_organization.zip IS 'address::zip';


--
-- Name: COLUMN app_user_organization.county; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN app_user_organization.county IS 'address::county';


--
-- Name: COLUMN app_user_organization.country; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN app_user_organization.country IS 'address::country';


--
-- Name: app_user_profiles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE app_user_profiles (
    last_name character(30),
    first_name character(30),
    middle_name character(30),
    date_of_birth date,
    id bigint NOT NULL,
    gender character(1),
    email_address character varying(200),
    cell_phone_number character varying(30),
    office_phone_number character varying(30),
    fax_number character varying(30)
);


ALTER TABLE public.app_user_profiles OWNER TO postgres;

--
-- Name: group_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.group_id_seq OWNER TO postgres;

--
-- Name: group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE group_id_seq OWNED BY app_organization_groups.id;


--
-- Name: members_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE members_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.members_id_seq OWNER TO postgres;

--
-- Name: members_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE members_id_seq OWNED BY app_group_members.id;


--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_id_seq OWNER TO postgres;

--
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE person_id_seq OWNED BY app_user_profiles.id;


--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_seq OWNER TO postgres;

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE role_id_seq OWNED BY app_group_auth.id;


--
-- Name: sys_address_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sys_address_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_address_types_id_seq OWNER TO postgres;

--
-- Name: sys_address_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE sys_address_types_id_seq OWNED BY app_sys_address_types.id;


--
-- Name: sys_roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sys_roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_roles_id_seq OWNER TO postgres;

--
-- Name: sys_roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE sys_roles_id_seq OWNED BY app_sys_roles.id;


--
-- Name: tenant_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tenant_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tenant_id_seq OWNER TO postgres;

--
-- Name: tenant_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tenant_id_seq OWNED BY app_user_organization.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_group_auth ALTER COLUMN id SET DEFAULT nextval('role_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_group_members ALTER COLUMN id SET DEFAULT nextval('members_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_organization_groups ALTER COLUMN id SET DEFAULT nextval('group_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_sys_address_types ALTER COLUMN id SET DEFAULT nextval('sys_address_types_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_sys_roles ALTER COLUMN id SET DEFAULT nextval('sys_roles_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_user_accounts ALTER COLUMN id SET DEFAULT nextval('account_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_user_address ALTER COLUMN id SET DEFAULT nextval('address_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_user_organization ALTER COLUMN id SET DEFAULT nextval('tenant_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_user_profiles ALTER COLUMN id SET DEFAULT nextval('person_id_seq'::regclass);


--
-- Name: account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('account_id_seq', 2, true);


--
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('address_id_seq', 2, true);


--
-- Data for Name: app_group_auth; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_group_auth (id, role_id, group_id) VALUES (5, 1, 1);
INSERT INTO app_group_auth (id, role_id, group_id) VALUES (6, 4, 4);
INSERT INTO app_group_auth (id, role_id, group_id) VALUES (7, 3, 2);


--
-- Data for Name: app_group_members; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_group_members (id, group_id, user_account_id) VALUES (1, 1, 1);
INSERT INTO app_group_members (id, group_id, user_account_id) VALUES (2, 2, 1);
INSERT INTO app_group_members (id, group_id, user_account_id) VALUES (3, 1, 2);
INSERT INTO app_group_members (id, group_id, user_account_id) VALUES (4, 4, 2);


--
-- Data for Name: app_organization_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_organization_groups (id, name, description, org_id) VALUES (1, 'Associate', 'Employee Group', 1);
INSERT INTO app_organization_groups (id, name, description, org_id) VALUES (2, 'HR', 'HR Group', 1);
INSERT INTO app_organization_groups (id, name, description, org_id) VALUES (3, 'Sales', 'Sales Group', 2);
INSERT INTO app_organization_groups (id, name, description, org_id) VALUES (4, 'Management', 'Management Group', 2);


--
-- Data for Name: app_sys_address_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_sys_address_types (id, name, description) VALUES (1, 'home', 'home address');
INSERT INTO app_sys_address_types (id, name, description) VALUES (2, 'business', 'business address');


--
-- Data for Name: app_sys_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_sys_roles (id, description, name) VALUES (1, 'ROLE_EMPLOYEE', 'employee user role');
INSERT INTO app_sys_roles (id, description, name) VALUES (2, 'ROLE_ADMIN', 'administrator user role');
INSERT INTO app_sys_roles (id, description, name) VALUES (4, 'ROLE_SALES', 'sales user role');
INSERT INTO app_sys_roles (id, description, name) VALUES (3, 'ROLE_SUPERVISOR', 'supervisor user role');
INSERT INTO app_sys_roles (id, description, name) VALUES (5, 'ROLE_EXECUTIVE', 'executives user role');
INSERT INTO app_sys_roles (id, description, name) VALUES (6, 'ROLE_EMPLOYEE', 'employee user role');


--
-- Data for Name: app_user_accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_user_accounts (id, user_profile_id, login_name, login_password, create_date, locked_date, validate_date, is_account_expired, is_credential_expired, is_account_valid, is_account_locked, account_expired_date, credential_expired_date) VALUES (1, 1, 'toy                                               ', 'toy', '2014-05-02', NULL, '2014-05-02', false, false, false, true, NULL, NULL);
INSERT INTO app_user_accounts (id, user_profile_id, login_name, login_password, create_date, locked_date, validate_date, is_account_expired, is_credential_expired, is_account_valid, is_account_locked, account_expired_date, credential_expired_date) VALUES (2, 2, 'admin                                             ', 'admin', '2014-05-02', NULL, '2014-05-02', false, false, false, true, NULL, NULL);


--
-- Data for Name: app_user_address; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_user_address (id, street1, street2, city, state, county, country, zip, type_id, user_profile_id, "isPrimary") VALUES (1, '2033 Rachel Ln                                    ', NULL, 'Round Rock                                        ', 'TX', 'Williamson                                        ', 'US                                                ', '78664', 1, 1, false);
INSERT INTO app_user_address (id, street1, street2, city, state, county, country, zip, type_id, user_profile_id, "isPrimary") VALUES (2, '1113 Tabernash Dr.                                ', NULL, 'Leander                                           ', 'TX', 'Williamson                                        ', 'US                                                ', '78641', 1, 2, false);


--
-- Data for Name: app_user_organization; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_user_organization (id, name, phone_number, fax_number, street1, street2, city, state, zip, county, country, email_address) VALUES (1, 'Conoco Philips', '1-800-456-9877', '1-800-578-4577', '233 N. Main Str.', NULL, 'Austin', 'TX', '78642', 'Williamson', 'USA', 'contact@conocophilips.com');
INSERT INTO app_user_organization (id, name, phone_number, fax_number, street1, street2, city, state, zip, county, country, email_address) VALUES (2, 'Shell', '1-800-989-5768', '1-800-457-4822', '1 Shell Drive', NULL, 'Austin', 'TX', '78640', 'Williamson', 'USA', 'contact@shell.com');


--
-- Data for Name: app_user_profiles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO app_user_profiles (last_name, first_name, middle_name, date_of_birth, id, gender, email_address, cell_phone_number, office_phone_number, fax_number) VALUES ('John                          ', 'Tan                           ', 'S                             ', '1988-01-01', 1, 'M', 'john.tan@gmail.com', '313-202-9087', '312-222-9766', '313-454-3432');
INSERT INTO app_user_profiles (last_name, first_name, middle_name, date_of_birth, id, gender, email_address, cell_phone_number, office_phone_number, fax_number) VALUES ('Sam                           ', 'Ray                           ', 'H                             ', '1979-02-22', 2, 'M', 'sam.ray@hotmail.com', '312-988-7866', '312-756-3299', '312-954-3498');


--
-- Name: group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('group_id_seq', 4, true);


--
-- Name: members_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('members_id_seq', 4, true);


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('person_id_seq', 2, true);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('role_id_seq', 7, true);


--
-- Name: sys_address_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sys_address_types_id_seq', 2, true);


--
-- Name: sys_roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sys_roles_id_seq', 6, true);


--
-- Name: tenant_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tenant_id_seq', 2, true);


--
-- Name: pk_address; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_user_address
    ADD CONSTRAINT pk_address PRIMARY KEY (id);


--
-- Name: pk_app_user_acct; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_user_accounts
    ADD CONSTRAINT pk_app_user_acct PRIMARY KEY (id);


--
-- Name: pk_app_user_org; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_user_organization
    ADD CONSTRAINT pk_app_user_org PRIMARY KEY (id);


--
-- Name: pk_app_user_profile; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_user_profiles
    ADD CONSTRAINT pk_app_user_profile PRIMARY KEY (id);


--
-- Name: pk_group; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_organization_groups
    ADD CONSTRAINT pk_group PRIMARY KEY (id);


--
-- Name: pk_grp_members; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_group_members
    ADD CONSTRAINT pk_grp_members PRIMARY KEY (id);


--
-- Name: pk_role; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_group_auth
    ADD CONSTRAINT pk_role PRIMARY KEY (id);


--
-- Name: pk_sys_address_types; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_sys_address_types
    ADD CONSTRAINT pk_sys_address_types PRIMARY KEY (id);


--
-- Name: pk_sys_roles; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY app_sys_roles
    ADD CONSTRAINT pk_sys_roles PRIMARY KEY (id);


--
-- Name: fk_acct_profile; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_user_accounts
    ADD CONSTRAINT fk_acct_profile FOREIGN KEY (user_profile_id) REFERENCES app_user_profiles(id);


--
-- Name: fk_address_sys; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_user_address
    ADD CONSTRAINT fk_address_sys FOREIGN KEY (type_id) REFERENCES app_sys_address_types(id);


--
-- Name: fk_auth_grp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_group_auth
    ADD CONSTRAINT fk_auth_grp FOREIGN KEY (group_id) REFERENCES app_organization_groups(id);


--
-- Name: fk_auth_role; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_group_auth
    ADD CONSTRAINT fk_auth_role FOREIGN KEY (role_id) REFERENCES app_sys_roles(id);


--
-- Name: fk_grp_org; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_organization_groups
    ADD CONSTRAINT fk_grp_org FOREIGN KEY (org_id) REFERENCES app_user_organization(id);


--
-- Name: fk_members_acct; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_group_members
    ADD CONSTRAINT fk_members_acct FOREIGN KEY (user_account_id) REFERENCES app_user_accounts(id);


--
-- Name: fk_members_grp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_group_members
    ADD CONSTRAINT fk_members_grp FOREIGN KEY (group_id) REFERENCES app_organization_groups(id);


--
-- Name: fk_user_address; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY app_user_address
    ADD CONSTRAINT fk_user_address FOREIGN KEY (user_profile_id) REFERENCES app_user_profiles(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

