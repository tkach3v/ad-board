DROP TABLE IF EXISTS public.board_user CASCADE;
CREATE TABLE public.board_user
(
    id bigserial NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(25) NOT NULL DEFAULT 'USER',
    status character varying(25) NOT NULL DEFAULT 'ACTIVE',
	rating real NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE (email)
);


DROP TABLE IF EXISTS public.category CASCADE;
CREATE TABLE public.category
(
    id bigserial NOT NULL,
    title character varying NOT NULL,
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS public.chat CASCADE;
CREATE TABLE public.chat
(
    id bigserial NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS public.user_chat CASCADE;
CREATE TABLE public.user_chat
(
    user_id bigint NOT NULL,
    chat_id bigint NOT NULL,
    PRIMARY KEY (user_id, chat_id),
    FOREIGN KEY (user_id)
        REFERENCES public.board_user (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    FOREIGN KEY (chat_id)
        REFERENCES public.chat (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

DROP TABLE IF EXISTS public.message CASCADE;
CREATE TABLE public.message
(
    id bigserial NOT NULL,
    content character varying(1024) NOT NULL,
    date date NOT NULL,
    status character varying(25) NOT NULL DEFAULT 'SENT',
    chat_id bigint NOT NULL,
    sender_id bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (chat_id)
        REFERENCES public.chat (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    FOREIGN KEY (sender_id)
        REFERENCES public.board_user (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

DROP TABLE IF EXISTS public.ad CASCADE;
CREATE TABLE public.ad
(
    id bigserial NOT NULL,
    title character varying(100) NOT NULL,
    description character varying(1024) NOT NULL,
    price double precision NOT NULL,
    status character varying(25) NOT NULL DEFAULT 'ACTIVE',
    creation_date date NOT NULL,
	sale_date date,
    promotion boolean NOT NULL DEFAULT FALSE,
    promotion_end_date date,
    owner_id bigint NOT NULL,
	category_id bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id)
        REFERENCES public.board_user (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
	FOREIGN KEY (category_id)
		REFERENCES public.category (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE
		NOT VALID
);

DROP TABLE IF EXISTS public.review CASCADE;
CREATE TABLE public.review
(
    id bigserial NOT NULL,
    score smallint NOT NULL,
    comment character varying(512) NOT NULL,
    author_id bigint NOT NULL,
    ad_id bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id)
        REFERENCES public.board_user (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    FOREIGN KEY (ad_id)
        REFERENCES public.ad (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CHECK (score >= 1 AND score <=5)
);
