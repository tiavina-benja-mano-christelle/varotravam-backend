CREATE SCHEMA IF NOT EXISTS "public";

CREATE VIEW "public".v_marque_vendu AS SELECT ma.id AS marque_id,
    ma.nom AS marque,
    count(*) AS nombre_ventes,
    a.etat
   FROM (((annonce a
     JOIN vehicule v ON ((v.id = a.vehicule_id)))
     JOIN modele m ON ((m.id = v.modele_id)))
     JOIN marque ma ON ((ma.id = m.marque_id)))
  GROUP BY ma.nom, ma.id, a.etat
  ORDER BY (count(*)) DESC;

CREATE VIEW "public".v_meilleur_vendeur AS SELECT u.id AS utilisateur_id,
    u.nom,
    count(*) AS nombre_vente,
    a.etat
   FROM (annonce a
     JOIN utilisateur u ON ((u.id = a.utilisateur_id)))
  GROUP BY u.nom, u.id, a.etat
  ORDER BY (count(*)) DESC;

CREATE VIEW "public".v_modele_vendu AS SELECT m.id AS modele_id,
    m.nom AS modele,
    count(*) AS nombre_ventes,
    a.etat
   FROM ((annonce a
     JOIN vehicule v ON ((v.id = a.vehicule_id)))
     JOIN modele m ON ((m.id = v.modele_id)))
  GROUP BY m.nom, m.id, a.etat
  ORDER BY (count(*)) DESC;

