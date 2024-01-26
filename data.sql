
CREATE SEQUENCE "public".annonce_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".categorie_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".commission_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".couleur_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".energie_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".equipement_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".favori_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".freinage_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".marque_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".modele_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".offre_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".transmission_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".utilisateur_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".vehicule_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE "public".vehicule_image_id_seq START WITH 1 INCREMENT BY 1;

CREATE  TABLE "public".annee ( 
	annee                integer  NOT NULL  ,
	CONSTRAINT pk_annee PRIMARY KEY ( annee )
 );

CREATE  TABLE "public".categorie ( 
	id                   integer DEFAULT nextval('categorie_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)  NOT NULL  ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT categorie_pkey PRIMARY KEY ( id ),
	CONSTRAINT categorie_nom_key UNIQUE ( nom ) 
 );

CREATE  TABLE "public".commission ( 
	id                   integer DEFAULT nextval('commission_id_seq'::regclass) NOT NULL  ,
	commission_date      date DEFAULT CURRENT_DATE NOT NULL  ,
	valeur               double precision  NOT NULL  ,
	CONSTRAINT pk_commission PRIMARY KEY ( id )
 );

CREATE  TABLE "public".couleur ( 
	id                   integer DEFAULT nextval('couleur_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)    ,
	valeur               char(7)    ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT couleur_pkey PRIMARY KEY ( id ),
	CONSTRAINT unq_couleur_valeur UNIQUE ( valeur ) ,
	CONSTRAINT unq_couleur_nom UNIQUE ( nom ) 
 );

CREATE  TABLE "public".energie ( 
	id                   integer DEFAULT nextval('energie_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)  NOT NULL  ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT energie_pkey PRIMARY KEY ( id ),
	CONSTRAINT energie_nom_key UNIQUE ( nom ) 
 );

CREATE  TABLE "public".equipement ( 
	id                   integer DEFAULT nextval('equipement_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)    ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT equipement_pkey PRIMARY KEY ( id ),
	CONSTRAINT unq_equipement UNIQUE ( nom ) 
 );

CREATE  TABLE "public".freinage ( 
	id                   integer DEFAULT nextval('freinage_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)  NOT NULL  ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT freinage_pkey PRIMARY KEY ( id ),
	CONSTRAINT freinage_nom_key UNIQUE ( nom ) 
 );

CREATE  TABLE "public".marque ( 
	id                   integer DEFAULT nextval('marque_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)  NOT NULL  ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT marque_pkey PRIMARY KEY ( id ),
	CONSTRAINT marque_nom_key UNIQUE ( nom ) 
 );

CREATE  TABLE "public".modele ( 
	id                   integer DEFAULT nextval('modele_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)  NOT NULL  ,
	marque_id            integer  NOT NULL  ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT modele_pkey PRIMARY KEY ( id ),
	CONSTRAINT unq_modele UNIQUE ( marque_id, nom ) ,
	CONSTRAINT modele_marque_id_fkey FOREIGN KEY ( marque_id ) REFERENCES "public".marque( id )   
 );

CREATE  TABLE "public".mois ( 
	id                   integer  NOT NULL  ,
	valeur               varchar(10)    ,
	CONSTRAINT pk_tbl PRIMARY KEY ( id )
 );

CREATE  TABLE "public".transmission ( 
	id                   integer DEFAULT nextval('transmission_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)  NOT NULL  ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT transmission_pkey PRIMARY KEY ( id ),
	CONSTRAINT transmission_nom_key UNIQUE ( nom ) 
 );

CREATE  TABLE "public".utilisateur ( 
	id                   integer DEFAULT nextval('utilisateur_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(50)    ,
	email                varchar(50)    ,
	mot_de_passe         varchar(50)    ,
	administrateur       boolean DEFAULT false NOT NULL  ,
	date_inscription     date DEFAULT CURRENT_DATE NOT NULL  ,
	CONSTRAINT utilisateur_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE "public".vehicule ( 
	id                   integer DEFAULT nextval('vehicule_id_seq'::regclass) NOT NULL  ,
	kilometrage          numeric(15,2)    ,
	puissance            integer    ,
	place                integer    ,
	porte                integer    ,
	consommation         numeric(15,2)    ,
	etat                 integer    ,
	transmission_id      integer  NOT NULL  ,
	energie_id           integer  NOT NULL  ,
	categorie_id         integer  NOT NULL  ,
	modele_id            integer  NOT NULL  ,
	freinage_id          integer  NOT NULL  ,
	couleur_id           integer  NOT NULL  ,
	CONSTRAINT vehicule_pkey PRIMARY KEY ( id ),
	CONSTRAINT vehicule_categorie_id_fkey FOREIGN KEY ( categorie_id ) REFERENCES "public".categorie( id )   ,
	CONSTRAINT fk_vehicule_couleur FOREIGN KEY ( couleur_id ) REFERENCES "public".couleur( id )   ,
	CONSTRAINT vehicule_energie_id_fkey FOREIGN KEY ( energie_id ) REFERENCES "public".energie( id )   ,
	CONSTRAINT vehicule_freinage_id_fkey FOREIGN KEY ( freinage_id ) REFERENCES "public".freinage( id )   ,
	CONSTRAINT vehicule_modele_id_fkey FOREIGN KEY ( modele_id ) REFERENCES "public".modele( id )   ,
	CONSTRAINT vehicule_transmission_id_fkey FOREIGN KEY ( transmission_id ) REFERENCES "public".transmission( id )   
 );

CREATE  TABLE "public".vehicule_equipement ( 
	vehicule_id          integer  NOT NULL  ,
	equipement_id        integer  NOT NULL  ,
	CONSTRAINT vehicule_equipement_pkey PRIMARY KEY ( vehicule_id, equipement_id ),
	CONSTRAINT vehicule_equipement_equipement_id_fkey FOREIGN KEY ( equipement_id ) REFERENCES "public".equipement( id )   ,
	CONSTRAINT vehicule_equipement_vehicule_id_fkey FOREIGN KEY ( vehicule_id ) REFERENCES "public".vehicule( id )   
 );

CREATE  TABLE "public".vehicule_image ( 
	id                   integer DEFAULT nextval('vehicule_image_id_seq'::regclass) NOT NULL  ,
	vehicule_id          integer  NOT NULL  ,
	valeur               text  NOT NULL  ,
	CONSTRAINT pk_vehicule_image PRIMARY KEY ( id ),
	CONSTRAINT fk_vehicule_image_vehicule FOREIGN KEY ( vehicule_id ) REFERENCES "public".vehicule( id )   
 );

CREATE  TABLE "public".annonce ( 
	id                   integer DEFAULT nextval('annonce_id_seq'::regclass) NOT NULL  ,
	prix_initial         numeric(15,2)  NOT NULL  ,
	date_publication     date DEFAULT CURRENT_DATE NOT NULL  ,
	date_fermeture       date    ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	description          text    ,
	vehicule_id          integer  NOT NULL  ,
	utilisateur_id       integer  NOT NULL  ,
	observation          text    ,
	CONSTRAINT annonce_pkey PRIMARY KEY ( id ),
	CONSTRAINT annonce_utilisateur_id_fkey FOREIGN KEY ( utilisateur_id ) REFERENCES "public".utilisateur( id )   ,
	CONSTRAINT annonce_vehicule_id_fkey FOREIGN KEY ( vehicule_id ) REFERENCES "public".vehicule( id )   
 );

CREATE  TABLE "public".favori ( 
	id                   integer DEFAULT nextval('favori_id_seq'::regclass) NOT NULL  ,
	utilisateur_id       integer  NOT NULL  ,
	annonce_id           integer  NOT NULL  ,
	etat                 integer DEFAULT 5 NOT NULL  ,
	CONSTRAINT favori_pkey PRIMARY KEY ( id ),
	CONSTRAINT unq_favori UNIQUE ( utilisateur_id, annonce_id ) ,
	CONSTRAINT favori_annonce_id_fkey FOREIGN KEY ( annonce_id ) REFERENCES "public".annonce( id )   ,
	CONSTRAINT favori_utilisateur_id_fkey FOREIGN KEY ( utilisateur_id ) REFERENCES "public".utilisateur( id )   
 );

CREATE  TABLE "public".offre ( 
	id                   integer DEFAULT nextval('offre_id_seq'::regclass) NOT NULL  ,
	prix_propose         numeric(15,2)  NOT NULL  ,
	prix_contre          numeric(15,2)    ,
	date_offre           date DEFAULT CURRENT_DATE NOT NULL  ,
	date_contre          date DEFAULT CURRENT_DATE   ,
	etat                 integer    ,
	utilisateur_id       integer  NOT NULL  ,
	annonce_id           integer  NOT NULL  ,
	CONSTRAINT offre_pkey PRIMARY KEY ( id ),
	CONSTRAINT offre_annonce_id_fkey FOREIGN KEY ( annonce_id ) REFERENCES "public".annonce( id )   ,
	CONSTRAINT offre_utilisateur_id_fkey FOREIGN KEY ( utilisateur_id ) REFERENCES "public".utilisateur( id )   
 );


CREATE VIEW "public".v_marque_vendu AS  SELECT ma.id AS marque_id,
    ma.nom AS marque,
    count(*) AS nombre_ventes,
    a.etat
   FROM (((annonce a
     JOIN vehicule v ON ((v.id = a.vehicule_id)))
     JOIN modele m ON ((m.id = v.modele_id)))
     JOIN marque ma ON ((ma.id = m.marque_id)))
  GROUP BY ma.nom, ma.id, a.etat
  ORDER BY (count(*)) DESC;

CREATE VIEW "public".v_meilleur_vendeur AS  SELECT u.id AS utilisateur_id,
    u.nom,
    count(*) AS nombre_vente,
    a.etat
   FROM (annonce a
     JOIN utilisateur u ON ((u.id = a.utilisateur_id)))
  GROUP BY u.nom, u.id, a.etat
  ORDER BY (count(*)) DESC;

CREATE VIEW "public".v_modele AS  SELECT mo.id,
    mo.nom,
    mo.marque_id,
    ma.nom AS marque,
    mo.etat
   FROM (modele mo
     JOIN marque ma ON ((mo.marque_id = ma.id)));

CREATE VIEW "public".v_modele_vendu AS  SELECT m.id AS modele_id,
    m.nom AS modele,
    count(*) AS nombre_ventes,
    a.etat
   FROM ((annonce a
     JOIN vehicule v ON ((v.id = a.vehicule_id)))
     JOIN modele m ON ((m.id = v.modele_id)))
  GROUP BY m.nom, m.id, a.etat
  ORDER BY (count(*)) DESC;

CREATE VIEW "public".v_month_year AS  SELECT mois.id mois
,
    mois.valeur,
    annee.annee
   FROM mois,
    annee;

CREATE VIEW "public".v_utilisateur_admin AS  SELECT utilisateur.id,
    utilisateur.nom,
    utilisateur.email,
    utilisateur.mot_de_passe,
    utilisateur.administrateur
   FROM utilisateur
  WHERE (utilisateur.administrateur IS TRUE);

CREATE VIEW "public".v_utilisateur_client AS  SELECT utilisateur.id,
    utilisateur.nom,
    utilisateur.email,
    utilisateur.mot_de_passe,
    utilisateur.administrateur,
    utilisateur.date_inscription,
    EXTRACT(year FROM utilisateur.date_inscription) AS annee_inscription,
    EXTRACT(month FROM utilisateur.date_inscription) AS mois_inscription
   FROM utilisateur
  WHERE (utilisateur.administrateur IS FALSE);

  

CREATE VIEW "public".v_graphe_utilisateur_inscrit AS  SELECT vmy.annee, vmy.mois, count(v.nom) nb_inscrit
FROM v_utilisateur_client v
RIGHT JOIN v_month_year vmy ON v.annee_inscription=vmy.annee AND v.mois_inscription=vmy.mois
GROUP BY vmy.annee, vmy.mois;



CREATE VIEW "public".v_vehicule_equipement AS  SELECT ve.vehicule_id,
    array_to_string(array_agg(((e.id || ':'::text) || (e.nom)::text)), ';'::text) AS equipements
   FROM (vehicule_equipement ve
     JOIN equipement e ON ((ve.equipement_id = e.id)))
  GROUP BY ve.vehicule_id;

CREATE VIEW "public".v_vehicule_image AS  SELECT vehicule_image.vehicule_id,
    array_to_string(array_agg(vehicule_image.valeur), ';'::text) AS images
   FROM vehicule_image
  GROUP BY vehicule_image.vehicule_id;

  
CREATE VIEW "public".v_vehicule AS  SELECT v.id AS vehicule_id,
    v.kilometrage,
    v.puissance,
    v.place,
    v.porte,
    v.consommation,
    v.etat AS etat_vehicule,
    v.transmission_id,
    v.energie_id,
    v.categorie_id,
    v.modele_id,
    v.freinage_id,
    v.couleur_id,
    t.nom AS transmission,
    e.nom AS energie,
    c.nom AS categorie,
    m.nom AS modele,
    f.nom AS freinage,
    co.nom AS couleur,
    ma.nom AS marque,
    ma.id AS marque_id,
    vve.equipements,
    vvi.images
   FROM (((((((((vehicule v
     JOIN transmission t ON ((t.id = v.transmission_id)))
     JOIN energie e ON ((e.id = v.energie_id)))
     JOIN categorie c ON ((c.id = v.categorie_id)))
     JOIN modele m ON ((m.id = v.modele_id)))
     JOIN marque ma ON ((ma.id = m.marque_id)))
     JOIN freinage f ON ((f.id = v.freinage_id)))
     JOIN couleur co ON ((co.id = v.couleur_id)))
     LEFT JOIN v_vehicule_equipement vve ON ((vve.vehicule_id = v.id)))
     LEFT JOIN v_vehicule_image vvi ON ((vvi.vehicule_id = v.id)));

     
CREATE VIEW "public".v_annonce AS  SELECT v.vehicule_id,
    v.kilometrage,
    v.puissance,
    v.place,
    v.porte,
    v.consommation,
    v.etat_vehicule,
    v.transmission_id,
    v.energie_id,
    v.categorie_id,
    v.modele_id,
    v.freinage_id,
    v.couleur_id,
    v.transmission,
    v.energie,
    v.categorie,
    v.modele,
    v.freinage,
    v.couleur,
    v.marque,
    v.marque_id,
    v.equipements,
    v.images,
    a.id AS annonce_id,
    a.prix_initial,
    a.date_publication,
    a.date_fermeture,
    a.etat AS etat_annonce,
    a.description,
    a.utilisateur_id,
    u.nom AS utilisateur_nom,
    u.email AS utilisateur_email,
    u.mot_de_passe
   FROM ((annonce a
     JOIN v_vehicule v ON ((a.vehicule_id = v.vehicule_id)))
     JOIN utilisateur u ON ((a.utilisateur_id = u.id)));

CREATE VIEW "public".v_annonce_favorites AS  SELECT f.utilisateur_id AS utilisateur_favori_id,
    v.vehicule_id,
    v.kilometrage,
    v.puissance,
    v.place,
    v.porte,
    v.consommation,
    v.etat_vehicule,
    v.transmission_id,
    v.energie_id,
    v.categorie_id,
    v.modele_id,
    v.freinage_id,
    v.couleur_id,
    v.transmission,
    v.energie,
    v.categorie,
    v.modele,
    v.freinage,
    v.couleur,
    v.marque,
    v.marque_id,
    v.equipements,
    v.images,
    a.id AS annonce_id,
    a.prix_initial,
    a.date_publication,
    a.date_fermeture,
    a.etat AS etat_annonce,
    a.description,
    a.utilisateur_id
   FROM ((favori f
     JOIN annonce a ON ((a.id = f.annonce_id)))
     JOIN v_vehicule v ON ((a.vehicule_id = v.vehicule_id)))
  WHERE (f.etat >= 5);

  
CREATE VIEW "public".v_annonce_vendu AS  SELECT v_annonce.vehicule_id,
    v_annonce.kilometrage,
    v_annonce.puissance,
    v_annonce.place,
    v_annonce.porte,
    v_annonce.consommation,
    v_annonce.etat_vehicule,
    v_annonce.transmission_id,
    v_annonce.energie_id,
    v_annonce.categorie_id,
    v_annonce.modele_id,
    v_annonce.freinage_id,
    v_annonce.couleur_id,
    v_annonce.transmission,
    v_annonce.energie,
    v_annonce.categorie,
    v_annonce.modele,
    v_annonce.freinage,
    v_annonce.couleur,
    v_annonce.marque,
    v_annonce.marque_id,
    v_annonce.equipements,
    v_annonce.images,
    v_annonce.annonce_id,
    v_annonce.prix_initial,
    v_annonce.date_publication,
    v_annonce.date_fermeture,
    v_annonce.etat_annonce,
    v_annonce.description,
    v_annonce.utilisateur_id,
    v_annonce.utilisateur_nom,
    v_annonce.utilisateur_email,
    v_annonce.mot_de_passe,
    COALESCE(o.prix_contre, o.prix_propose) AS prix_vente
   FROM (v_annonce
     LEFT JOIN offre o ON (((v_annonce.annonce_id = o.annonce_id) AND (o.etat = 20))))
  WHERE (v_annonce.etat_annonce = 20);

INSERT INTO "public".annee( annee ) VALUES ( 2020);
INSERT INTO "public".annee( annee ) VALUES ( 2021);
INSERT INTO "public".annee( annee ) VALUES ( 2022);
INSERT INTO "public".annee( annee ) VALUES ( 2023);
INSERT INTO "public".annee( annee ) VALUES ( 2024);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 2, 'SUV', 5);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 3, 'Coupé', 5);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 5, 'Cabriolet', 5);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 11, 'Limousine', 5);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 4, 'Hatchback', 0);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 1, 'Sedan', 0);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 16, 'camion', 0);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 17, '3 roue', 0);
INSERT INTO "public".categorie( id, nom, etat ) VALUES ( 18, 'Tiavina', 0);
INSERT INTO "public".commission( id, commission_date, valeur ) VALUES ( 1, '2024-01-26', 25.0);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 99, 'Rouge pourpre', '#400000', 0);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 1, 'Rouge', '#FF0000', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 2, 'Rouge clair', '#FF6666', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 3, 'Rouge foncé', '#990000', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 4, 'Vert', '#008000', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 5, 'Vert clair', '#66FF66', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 6, 'Vert foncé', '#004C00', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 7, 'Bleu', '#0000FF', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 8, 'Bleu clair', '#6666FF', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 9, 'Bleu foncé', '#000099', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 10, 'Jaune', '#FFFF00', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 11, 'Jaune clair', '#FFFF99', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 12, 'Jaune foncé', '#999900', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 13, 'Orange', '#FFA500', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 14, 'Orange clair', '#FFCC66', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 15, 'Orange foncé', '#996300', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 16, 'Violet', '#800080', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 17, 'Violet clair', '#CC66CC', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 18, 'Violet foncé', '#660066', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 19, 'Rose', '#FFC0CB', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 20, 'Rose clair', '#FFD1DC', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 21, 'Rose foncé', '#FF6699', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 22, 'Brun', '#A52A2A', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 23, 'Brun clair', '#CC9966', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 24, 'Brun foncé', '#663300', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 25, 'Gris', '#808080', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 26, 'Gris clair', '#C0C0C0', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 27, 'Gris foncé', '#404040', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 28, 'Noir', '#000000', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 29, 'Blanc', '#FFFFFF', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 30, 'Blanc cassé', '#F5F5F5', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 31, 'Gris clair neutre', '#D3D3D3', 5);
INSERT INTO "public".couleur( id, nom, valeur, etat ) VALUES ( 32, 'Gris foncé neutre', '#A9A9A9', 5);
INSERT INTO "public".energie( id, nom, etat ) VALUES ( 1, 'Essence', 5);
INSERT INTO "public".energie( id, nom, etat ) VALUES ( 2, 'Diesel', 5);
INSERT INTO "public".energie( id, nom, etat ) VALUES ( 3, 'Hybride', 5);
INSERT INTO "public".energie( id, nom, etat ) VALUES ( 4, 'Électrique', 5);
INSERT INTO "public".energie( id, nom, etat ) VALUES ( 5, 'ARIA', 0);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 1, 'Climatisation', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 2, 'Système de navigation GPS', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 3, 'Toit ouvrant', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 4, 'Sièges en cuir', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 5, 'Caméra de recul', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 6, 'Régulateur de vitesse', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 7, 'Bluetooth', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 8, 'Jantes en alliage', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 9, 'Système audio haut de gamme', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 10, 'Capteurs de stationnement', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 11, 'Volant chauffant', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 12, 'Démarreur à distance', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 13, 'Écrans vidéo arrière', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 14, 'Aide au stationnement automatique', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 15, 'Sièges chauffants', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 16, 'Système de divertissement pour passagers', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 17, 'Toit panoramique', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 18, 'Phares automatiques', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 19, 'Assistance au freinage d''urgence', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 20, 'Avertisseur d''angle mort', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 21, 'Détection de collision', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 22, 'Rétroviseurs électriques chauffants', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 23, 'Hayon électrique', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 24, 'Système d''alarme', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 25, 'Réglage électrique des sièges', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 26, 'Connexion Wi-Fi embarquée', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 27, 'Assistant de maintien de voie', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 28, 'Système de démarrage sans clé', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 29, 'Chargeur sans fil pour téléphone', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 30, 'Sièges ventilés', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 31, 'Régulateur de vitesse adaptatif', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 32, 'Système de surveillance de la pression des pneus', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 33, 'Phares antibrouillard', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 34, 'Sièges arrière rabattables', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 35, 'Port USB', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 36, 'Capteurs de pluie', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 37, 'Système de divertissement avant', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 38, 'Système d''entrée sans clé', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 39, 'Porte-gobelet chauffant et refroidissant', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 40, 'Support lombaire électrique', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 41, 'Porte-bagages', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 42, 'Pare-brise chauffant', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 43, 'Chargeur USB', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 44, 'Rétroviseurs électriques repliables', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 45, 'Volant réglable électriquement', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 46, 'Hayon à ouverture automatique', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 47, 'Caméra à 360 degrés', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 48, 'Chargeur EV intégré', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 49, 'Éclairage d''ambiance', 5);
INSERT INTO "public".equipement( id, nom, etat ) VALUES ( 50, 'Prises de courant domestiques intégrées', 5);
INSERT INTO "public".freinage( id, nom, etat ) VALUES ( 1, 'Freins à disque', 5);
INSERT INTO "public".freinage( id, nom, etat ) VALUES ( 2, 'Freins à tambour', 5);
INSERT INTO "public".freinage( id, nom, etat ) VALUES ( 3, 'Freins ABS', 5);
INSERT INTO "public".freinage( id, nom, etat ) VALUES ( 6, 'tiavina', 0);
INSERT INTO "public".freinage( id, nom, etat ) VALUES ( 5, 'Freins double', 0);
INSERT INTO "public".freinage( id, nom, etat ) VALUES ( 4, 'Freins antiblocage', 0);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 1, 'Toyota', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 2, 'Honda', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 3, 'Ford', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 4, 'Chevrolet', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 5, 'Volkswagen', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 6, 'Nissan', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 7, 'BMW', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 8, 'Mercedes-Benz', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 9, 'Audi', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 10, 'Hyundai', 5);
INSERT INTO "public".marque( id, nom, etat ) VALUES ( 11, 'Peugeot', 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 1, 'Camry', 1, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 2, 'Corolla', 1, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 3, 'Rav4', 1, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 4, 'Highlander', 1, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 5, 'Prius', 1, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 6, 'Accord', 2, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 7, 'Civic', 2, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 8, 'CR-V', 2, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 9, 'Pilot', 2, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 10, 'Fit', 2, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 11, 'Fusion', 3, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 12, 'Escape', 3, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 13, 'Explorer', 3, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 14, 'F-150', 3, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 15, 'Mustang', 3, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 16, 'Malibu', 4, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 17, 'Equinox', 4, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 18, 'Traverse', 4, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 19, 'Silverado', 4, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 20, 'Camaro', 4, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 21, 'Jetta', 5, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 22, 'Passat', 5, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 23, 'Tiguan', 5, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 24, 'Atlas', 5, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 25, 'Golf', 5, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 26, 'Altima', 6, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 27, 'Maxima', 6, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 28, 'Rogue', 6, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 29, 'Pathfinder', 6, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 30, 'Leaf', 6, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 31, '3 Series', 7, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 32, '5 Series', 7, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 33, 'X3', 7, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 34, 'X5', 7, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 35, '7 Series', 7, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 36, 'C-Class', 8, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 37, 'E-Class', 8, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 38, 'GLC', 8, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 39, 'GLE', 8, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 40, 'S-Class', 8, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 41, 'A3', 9, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 42, 'A4', 9, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 43, 'Q5', 9, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 44, 'Q7', 9, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 45, 'A6', 9, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 46, 'Elantra', 10, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 47, 'Sonata', 10, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 48, 'Tucson', 10, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 49, 'Santa Fe', 10, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 50, 'Kona', 10, 5);
INSERT INTO "public".modele( id, nom, marque_id, etat ) VALUES ( 52, '1B', 9, 0);
INSERT INTO "public".mois( id, valeur ) VALUES ( 1, 'Janvier');
INSERT INTO "public".mois( id, valeur ) VALUES ( 2, 'Fevrier');
INSERT INTO "public".mois( id, valeur ) VALUES ( 3, 'Mars');
INSERT INTO "public".mois( id, valeur ) VALUES ( 4, 'Avril');
INSERT INTO "public".mois( id, valeur ) VALUES ( 5, 'Mai');
INSERT INTO "public".mois( id, valeur ) VALUES ( 6, 'Juin');
INSERT INTO "public".mois( id, valeur ) VALUES ( 7, 'Juillet');
INSERT INTO "public".mois( id, valeur ) VALUES ( 8, 'Aout');
INSERT INTO "public".mois( id, valeur ) VALUES ( 9, 'Septembre');
INSERT INTO "public".mois( id, valeur ) VALUES ( 10, 'Octobre');
INSERT INTO "public".mois( id, valeur ) VALUES ( 11, 'Novembre');
INSERT INTO "public".mois( id, valeur ) VALUES ( 12, 'Decembre');
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 37, '', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 21, 'A1', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 23, 'A2', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 24, 'A3', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 25, 'A4', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 38, 'T1', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 39, 'T2', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 40, 'T3', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 2, 'Automatique', 5);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 31, 'A10', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 26, 'A5', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 27, 'A6', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 28, 'A7', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 29, 'A8', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 30, 'A9', 0);
INSERT INTO "public".transmission( id, nom, etat ) VALUES ( 1, 'Manuelle', 5);
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 1, 'John Doe', 'john.doe@email.com', 'motdepasse1', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 2, 'Jane Doe', 'jane.doe@email.com', 'motdepasse2', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 3, 'Alice Smith', 'alice.smith@email.com', 'motdepasse3', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 4, 'Bob Johnson', 'bob.johnson@email.com', 'motdepasse4', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 5, 'Eva Green', 'eva.green@email.com', 'motdepasse5', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 6, 'Charlie Brown', 'charlie.brown@email.com', 'motdepasse6', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 7, 'Sophie White', 'sophie.white@email.com', 'motdepasse7', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 8, 'Daniel Black', 'daniel.black@email.com', 'motdepasse8', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 9, 'Olivia Davis', 'olivia.davis@email.com', 'motdepasse9', false, '2024-01-26');
INSERT INTO "public".utilisateur( id, nom, email, mot_de_passe, administrateur, date_inscription ) VALUES ( 10, 'Michael Wilson', 'michael.wilson@email.com', 'motdepasse10', true, '2024-01-26');
INSERT INTO "public".vehicule( id, kilometrage, puissance, place, porte, consommation, etat, transmission_id, energie_id, categorie_id, modele_id, freinage_id, couleur_id ) VALUES ( 1, 50000.50, 150, 5, 4, 8.50, 2, 1, 2, 1, 1, 1, 3);
INSERT INTO "public".vehicule( id, kilometrage, puissance, place, porte, consommation, etat, transmission_id, energie_id, categorie_id, modele_id, freinage_id, couleur_id ) VALUES ( 2, 30000.25, 120, 5, 4, 7, 3, 2, 1, 2, 2, 2, 2);
INSERT INTO "public".vehicule( id, kilometrage, puissance, place, porte, consommation, etat, transmission_id, energie_id, categorie_id, modele_id, freinage_id, couleur_id ) VALUES ( 3, 30000.25, 120, 5, 4, 7, 3, 2, 1, 2, 4, 1, 2);
INSERT INTO "public".vehicule( id, kilometrage, puissance, place, porte, consommation, etat, transmission_id, energie_id, categorie_id, modele_id, freinage_id, couleur_id ) VALUES ( 4, 70000.75, 180, 7, 5, 9.20, 1, 1, 3, 3, 5, 3, 4);
INSERT INTO "public".vehicule( id, kilometrage, puissance, place, porte, consommation, etat, transmission_id, energie_id, categorie_id, modele_id, freinage_id, couleur_id ) VALUES ( 18, 15000, 150, 5, 5, 6.50, 8, 2, 2, 2, 3, 4, 7);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 1, 1);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 1, 2);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 1, 3);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 1, 4);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 1, 5);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 2, 6);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 2, 7);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 2, 8);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 2, 9);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 2, 10);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 3, 11);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 3, 12);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 3, 13);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 3, 14);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 3, 15);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 4, 46);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 4, 47);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 4, 48);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 4, 49);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 4, 50);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 18, 1);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 18, 5);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 18, 9);
INSERT INTO "public".vehicule_equipement( vehicule_id, equipement_id ) VALUES ( 18, 10);
INSERT INTO "public".vehicule_image( id, vehicule_id, valeur ) VALUES ( 1, 1, 'https://firebasestorage.googleapis.com/v0/b/supple-antenna-379817.appspot.com/o/images%2Fbasketball-nba-logo-wh8ae0q74gl3payt.jpg');
INSERT INTO "public".vehicule_image( id, vehicule_id, valeur ) VALUES ( 2, 1, 'https://firebasestorage.googleapis.com/v0/b/supple-antenna-379817.appspot.com/o/images%2Fimages.jpeg');
INSERT INTO "public".vehicule_image( id, vehicule_id, valeur ) VALUES ( 3, 18, 'https://firebasestorage.googleapis.com/v0/b/supple-antenna-379817.appspot.com/o/images%2Fbasketball-nba-logo-wh8ae0q74gl3payt.jpg');
INSERT INTO "public".vehicule_image( id, vehicule_id, valeur ) VALUES ( 4, 18, 'https://firebasestorage.googleapis.com/v0/b/supple-antenna-379817.appspot.com/o/images%2Fimages.jpeg');
INSERT INTO "public".annonce( id, prix_initial, date_publication, date_fermeture, etat, description, vehicule_id, utilisateur_id, observation ) VALUES ( 1, 5000000, '2024-01-12', null, 20, 'Voiture de luxe adapté à n''importe quel route', 1, 1, null);
INSERT INTO "public".annonce( id, prix_initial, date_publication, date_fermeture, etat, description, vehicule_id, utilisateur_id, observation ) VALUES ( 3, 6000000, '2024-01-17', null, 20, 'Voitures spacieuces', 18, 1, 'Demande trop explicite');
INSERT INTO "public".favori( id, utilisateur_id, annonce_id, etat ) VALUES ( 2, 1, 1, 5);