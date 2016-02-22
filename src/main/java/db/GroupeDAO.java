package db;

import com.google.common.base.Optional;
import com.google.common.collect.Iterators;
import core.Adresse;
import core.Client;
import core.Groupe;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tidus on 08/01/2016.
 */
public class GroupeDAO extends AbstractDAO<Groupe> {
    private ClientDAO cdao;

    public GroupeDAO(SessionFactory sessionFactory, ClientDAO cdao) {
        super(sessionFactory);
        this.cdao = cdao;
    }

    public Optional<Groupe> findById(long id) {
        return Optional.fromNullable(get(id));
    }

    public Groupe create(Groupe groupe) {
        return persist(groupe);
    }

    public List<Groupe> findAll() {
        return list(namedQuery("Groupe.findAll"));
    }

    /**
     * Fonction permettant de transformer un cluster en liste de groupe
     * @return List<Groupe></Groupe>
     */
    public List<Groupe> insertCluster() {
        // On récupère les clusters
        ArrayList clusters = cdao.getCluster();
        System.out.println("cluster : " + clusters.toString());
        // On créer un itérator sur la liste de clusters pour pouvoir la parcourir
        Iterator<ArrayList> itC = clusters.iterator();
        // On créer une liste qui sera celle renvoyé à la fin
        List<Groupe> lGroupe = new ArrayList<Groupe>();
        // On parcourt notre liste de clusters
        while (itC.hasNext()) {
            // On récupère notre groupe
            ArrayList itcNext = itC.next();
            System.out.println(itcNext);
            // On créer un itérator sur notre groupe pour récupérer les id des clients
            Iterator<Long> itlistId = itcNext.iterator();
            // On créer une liste de clients correspondant à notre groupe
            List<Client> listCli = new ArrayList<Client>();
            // On créer notre groupe vide (afin de pouvoir modifier notre client)
            Groupe gAdd = new Groupe();
            // On persist notre groupe
            create(gAdd);
            // On parcourt notre itérateur pour récupérer chaque client
            while (itlistId.hasNext()) {
                // On récupère l'id du client
                long id = itlistId.next();
                // On récupère le client
                Optional<Client> cli = cdao.findById(id);
                // Si le client existe
                if(cli.isPresent()) {
                    // On récupère le client
                    Client c = cli.get();
                    // On l'ajoute a notre liste de client
                    listCli.add(cli.get());
                    // On set le groupe à notre client
                    cdao.updateGroupe(c, gAdd);
                    // On met à jour (persist) notre client
                }
            }
            // On set la liste de client à notre groupe
            gAdd.setClients(listCli);
            // On met à jour le groupe
            create(gAdd);
            // On ajoute notre groupe à la liste des groupes
            lGroupe.add(gAdd);
        }
        // On renvoie la liste des groupes
        return lGroupe;
    }


    public Groupe getByClient(long id) {
        Optional<Client> cli = cdao.findById(id);
        if (cli.isPresent()) {
            Client realCli = cli.get();
            List<Groupe> lGroupe = findAll();
            Iterator itGroupe = lGroupe.iterator();
            while (itGroupe.hasNext()) {
                Groupe g = (Groupe) itGroupe.next();
                if (g.getClients().contains(realCli)) {
                    return g;
                }
            }
        }
        return null;
    }
}
