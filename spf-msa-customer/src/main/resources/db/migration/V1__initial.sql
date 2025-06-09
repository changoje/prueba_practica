--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.26
-- Dumped by pg_dump version 9.4.26
-- Started on 2023-04-21 11:32:38

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 174 (class 1259 OID 150998)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.cliente (
    id integer NOT NULL,
    direccion character varying(100) NOT NULL,
    edad integer NOT NULL,
    genero character varying(1) NOT NULL,
    identificacion character varying(10) NOT NULL,
    nombre character varying(100) NOT NULL,
    telefono character varying(10) NOT NULL,
    contrasenia character varying(255) NOT NULL,
    estado boolean NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 150996)
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_seq OWNER TO postgres;

--
-- TOC entry 2026 (class 0 OID 0)
-- Dependencies: 173
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_id_seq OWNED BY public.cliente.id;


--
-- TOC entry 176 (class 1259 OID 151006)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.cuenta (
    id_cuenta bigint NOT NULL,
    estado boolean NOT NULL,
    id_cliente integer NOT NULL,
    numero_cuenta character varying(255) NOT NULL,
    saldo_inicial numeric(19,2) NOT NULL,
    tipo_cuenta character varying(1) NOT NULL
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 151004)
-- Name: cuenta_id_cuenta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cuenta_id_cuenta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cuenta_id_cuenta_seq OWNER TO postgres;

--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 175
-- Name: cuenta_id_cuenta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_id_cuenta_seq OWNED BY public.cuenta.id_cuenta;


--
-- TOC entry 178 (class 1259 OID 151014)
-- Name: movimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.movimiento (
    id_movimientos bigint NOT NULL,
    fecha timestamp without time zone NOT NULL,
    id_cuenta bigint NOT NULL,
    saldo numeric(19,2) NOT NULL,
    tipo_movimiento character varying(1) NOT NULL,
    valor numeric(19,2) NOT NULL,
    numero_cuenta character varying(255) NOT NULL
);


ALTER TABLE public.movimiento OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 151012)
-- Name: movimiento_id_movimientos_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movimiento_id_movimientos_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movimiento_id_movimientos_seq OWNER TO postgres;

--
-- TOC entry 2028 (class 0 OID 0)
-- Dependencies: 177
-- Name: movimiento_id_movimientos_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movimiento_id_movimientos_seq OWNED BY public.movimiento.id_movimientos;


--
-- TOC entry 1893 (class 2604 OID 151001)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id SET DEFAULT nextval('public.cliente_id_seq'::regclass);


--
-- TOC entry 1894 (class 2604 OID 151009)
-- Name: id_cuenta; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta ALTER COLUMN id_cuenta SET DEFAULT nextval('public.cuenta_id_cuenta_seq'::regclass);


--
-- TOC entry 1895 (class 2604 OID 151017)
-- Name: id_movimientos; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento ALTER COLUMN id_movimientos SET DEFAULT nextval('public.movimiento_id_movimientos_seq'::regclass);


--
-- TOC entry 2014 (class 0 OID 150998)
-- Dependencies: 174
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (id, direccion, edad, genero, identificacion, nombre, telefono, contrasenia, estado) FROM stdin;
1	Otavalo sn y principal	26	M	0100561430	Jose Lema	0978654321	p.1234	t
2	Amazonas y  NNUU	35	F	0704907922	Marianela Montalvo	9999999999	5678	t
3	13 junio y Equinoccial	41	M	0100561430	Juan Osorio	0908874587	1245	t
\.


--
-- TOC entry 2029 (class 0 OID 0)
-- Dependencies: 173
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_seq', 3, true);


--
-- TOC entry 2016 (class 0 OID 151006)
-- Dependencies: 176
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cuenta (id_cuenta, estado, id_cliente, numero_cuenta, saldo_inicial, tipo_cuenta) FROM stdin;
2	t	1	478758	100.00	C
1	t	2	225487	2000.00	A
3	t	3	495878	0.00	A
4	t	2	496825	100.00	A
5	t	1	585545	1000.00	C
\.


--
-- TOC entry 2030 (class 0 OID 0)
-- Dependencies: 175
-- Name: cuenta_id_cuenta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_id_cuenta_seq', 5, true);


--
-- TOC entry 2018 (class 0 OID 151014)
-- Dependencies: 178
-- Data for Name: movimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.movimiento (id_movimientos, fecha, id_cuenta, saldo, tipo_movimiento, valor, numero_cuenta) FROM stdin;
1	2024-09-04 00:00:00	1	150.00	D	250.00	478758
3	2023-04-21 00:00:00	1	200.00	D	50.00	478758
5	2023-04-21 01:56:31.604132	1	125.00	R	-25.00	478758
6	2023-04-21 01:59:34.8267	1	250.00	D	100.00	478758
7	2023-04-21 11:01:44.033477	1	1150.00	D	1000.00	225487
8	2023-04-21 11:02:24.527254	1	140.00	R	-10.00	225487
9	2023-04-21 11:04:00.033184	1	1150.00	D	1000.00	478758
10	2023-04-21 11:05:58.277359	1	93.00	R	-57.00	478758
11	2023-04-21 11:07:33.912808	1	1150.00	D	1000.00	478758
12	2023-04-21 11:08:45.186122	5	1000.00	D	1000.00	478758
13	2023-04-21 11:10:48.981033	5	900.00	R	-100.00	585545
14	2023-04-21 11:11:08.027097	5	325.00	R	-575.00	585545
\.


--
-- TOC entry 2031 (class 0 OID 0)
-- Dependencies: 177
-- Name: movimiento_id_movimientos_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movimiento_id_movimientos_seq', 14, true);


--
-- TOC entry 1897 (class 2606 OID 151003)
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- TOC entry 1899 (class 2606 OID 151011)
-- Name: cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (id_cuenta);


--
-- TOC entry 1901 (class 2606 OID 151019)
-- Name: movimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT movimiento_pkey PRIMARY KEY (id_movimientos);


--
-- TOC entry 1903 (class 2606 OID 151025)
-- Name: fk8veysyanipny5mpudj13t8873; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT fk8veysyanipny5mpudj13t8873 FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(id_cuenta);


--
-- TOC entry 1902 (class 2606 OID 151020)
-- Name: fk_cuenta_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT fk_cuenta_cliente FOREIGN KEY (id_cliente) REFERENCES public.cliente(id);


--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-04-21 11:32:38

--
-- PostgreSQL database dump complete
--

