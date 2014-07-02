package com.projetIF4.planning;

import com.projetIF4.controller.EnseignantControleur;
import com.projetIF4.controller.PfeControleur;
import com.projetIF4.controller.SalleControleur;
import com.projetIF4.controller.SectionControleur;
import com.projetIF4.model.Enseignant;
import com.projetIF4.model.Pfe;
import com.projetIF4.model.Salle;
import com.projetIF4.model.Section;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Satellite
 */
@ManagedBean
@ViewScoped
public class PlanningPFEcontroleur implements Serializable{
    
    //les variable de calendrier
    private ScheduleModel eventModel;
    //salle selectionne pour affichage
    private Salle selectedSalle;
    private Section selectedSection;
    
    private List<Pfe> toutProjetPlanifier;
    private List<Pfe> toutProjetNonPlanifier;
    private List<Pfe> toutProjetNonPlanifierChercher;
    
    
    private Soutenance event = new Soutenance();
    
    //pfe selection
    private Pfe selectedPfe;
    
    //les attribue pour  recherche:
    //nom a chercher
    private String nomAchercher;
    //les filtre sans presidant sans ...
    private List<String> filtreListNonplanifier;
    
    
    private String duree_Soutenance;
    private Date heure_soutenance;
    private Date date_soutenance;
    
    private List<Salle> toutSalle;    
    private List<Salle> filtreSalle;
    private List<Enseignant> toutenseignants;
    private List<Enseignant> filtreenseignants;
    
    private List<Section> sectionTerminal;

    /**
     * Creates a new instance of PlanningPFEcontroleur
     */
    public PlanningPFEcontroleur() {
        nomAchercher="";        
        FacesContext context = FacesContext.getCurrentInstance();
        selectedSalle =(Salle)context.getExternalContext().getSessionMap().get("salle");
        selectedSection=(Section)context.getExternalContext().getSessionMap().get("section");
        
    }
    
    @PostConstruct
        public void postConstruction(){
            toutSalle=SalleControleur.getAllSalles();
            toutenseignants=EnseignantControleur.getAllEnseignant();
            sectionTerminal=SectionControleur.getAllSectionTerminal(true);
            updateCalendar();
        }
    
    //tranforme la duree de projet en un date c a dire date =dateprojet + duree
    public Date convertion(Date d,String duree){
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);           
            String[] t = duree.split(":");
            int dureeHeures = Integer.parseInt(t[0]);
            int dureeMinutes = Integer.parseInt(t[1]);
            cal.add(Calendar.HOUR, dureeHeures);
            cal.add(Calendar.MINUTE, dureeMinutes);
            return cal.getTime();
}
    public boolean intersection2Enseignant(Pfe e,Pfe es){
        if(e.getEncadreur()!=null){
            if(es.getEncadreur()!=null)
                if(e.getEncadreur().equals(es.getEncadreur()))
                        return true;
            if(es.getChefjury()!=null)
                     if( e.getEncadreur().equals(es.getChefjury()))
                             return true;
            if(es.getRapporteur()!=null)
                     if(e.getEncadreur().equals(es.getRapporteur()))
                            return true;        
        }
        if(e.getChefjury()!=null){
            if(es.getEncadreur()!=null)
                if(e.getChefjury().equals(es.getEncadreur()))
                        return true;
            if(es.getChefjury()!=null)
                     if( e.getChefjury().equals(es.getChefjury()))
                             return true;
            if(es.getRapporteur()!=null)
                     if(e.getChefjury().equals(es.getRapporteur()))
                            return true;        
        }
         if(e.getRapporteur()!=null){
            if(es.getEncadreur()!=null)
                if(e.getRapporteur().equals(es.getEncadreur()))
                        return true;
            if(es.getChefjury()!=null)
                     if( e.getRapporteur().equals(es.getChefjury()))
                             return true;
            if(es.getRapporteur()!=null)
                     if(e.getRapporteur().equals(es.getRapporteur()))
                            return true;        
        }    
    return false;
    }
    public boolean intersection2PFE(Pfe e,Pfe es){
            Date date1 = e.getDatesoutenance();
            Date dateduree1=convertion(date1, e.getDureesoutenance());
            Date date2 = es.getDatesoutenance();
            Date dateduree2=convertion(date2, es.getDureesoutenance());
            if((date1.compareTo(date2)>=0&& date1.compareTo(dateduree2)<=0)
               ||(dateduree1.compareTo(date2)>=0&&dateduree1.compareTo(dateduree2)<=0) 
               ||(date2.compareTo(date1)>=0&&date2.compareTo(dateduree1)<=0)
               ||(dateduree2.compareTo(date1)>=0&&dateduree2.compareTo(dateduree1)<=0)){
                return true;          
            }         
        return false;   
    }     
    public boolean intersectionSalle(Pfe e,Pfe es){
        if(e.getSalle()!=null){
        if(es.getSalle().equals(e.getSalle())){
        return true;
        }}
        return false;
}
    
    public void update() {
        Date date = selectedPfe.getDatesoutenance();
        boolean disponile=true;
        String message="";
        if (date != null) {
            Date date_final = new Date(heure_soutenance.getTime() + date.getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date_final);
            cal.add(Calendar.HOUR, 1);
            selectedPfe.setDatesoutenance(cal.getTime());
            selectedPfe.setDureesoutenance(duree_Soutenance);            
            for (Pfe p : toutProjetPlanifier){
                if(!selectedPfe.equals(p)){
                    //salle indisponible
                    if(intersection2PFE(selectedPfe,p)
                       &&intersectionSalle(selectedPfe,p)){
                        disponile=false;
                        message=message+" Salle indisponible";
                    }
                    //enseignant indisponible
                     if(intersection2PFE(selectedPfe,p)                       
                            &&intersection2Enseignant(selectedPfe,p)){
                        disponile=false;
                        message=message+" Enseignant indisponible";
                    }      
                          
                
                if(!disponile)
                    break;
                }
            }}
            if(disponile){
                if (selectedPfe.update()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Mise à jour avec succés."));
                    updateCalendar();
                    System.err.println("succes");
                 } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Mise à jour échouée."));
            System.err.println("ECHEC !!! " + selectedPfe.getDatesoutenance());
        }
        }else{
            System.out.println("ajout echoué"+message);}         
    }
    
    public void annulerProjet(){    
        selectedPfe.setDatesoutenance(null);
        selectedPfe.setSalle(null);
        selectedPfe.setDureesoutenance(null);
        selectedPfe.update();
        updateCalendar();
    }
        
    
    
        public void addprojet(Pfe p){
            Calendar cal = Calendar.getInstance();
            Date date = p.getDatesoutenance();
            cal.setTime(date);
            // traitement de la duree de soutenance
            String s = p.getDureesoutenance();
            String[] t = s.split(":");
            int dureeHeures = Integer.parseInt(t[0]);
            int dureeMinutes = Integer.parseInt(t[1]);
            cal.add(Calendar.HOUR, dureeHeures);
            cal.add(Calendar.MINUTE, dureeMinutes);
            eventModel.addEvent(new Soutenance(p, date, cal.getTime()));       
        }
        
        public void eliminerEnseignant(Pfe pfe){
            filtreenseignants=new ArrayList<>(toutenseignants);
            if(pfe.getChefjury()!=null){
            filtreenseignants.remove(pfe.getChefjury());}
            if(pfe.getRapporteur()!=null){
            filtreenseignants.remove(pfe.getRapporteur());}
            if(pfe.getEncadreur()!=null){
            filtreenseignants.remove(pfe.getEncadreur());}
        }
        public void initeliminerEnseignant(){
            if(selectedPfe!=null){
            eliminerEnseignant(selectedPfe);
            if(selectedPfe.getChefjury()!=null){
            filtreenseignants.add(selectedPfe.getChefjury());} }                 
        }
    
        public String affectenseignant(FlowEvent event){/***pour dialog wizard***/
            eliminerEnseignant(selectedPfe);
            switch(event.getNewStep()){
                case "President":if(selectedPfe.getChefjury()!=null){
            filtreenseignants.add(selectedPfe.getChefjury());}  break;
                case "Rapporteur":if(selectedPfe.getRapporteur()!=null){
            filtreenseignants.add(selectedPfe.getRapporteur());}  break;
                    default: break;
            }
            System.err.println(""+event.getOldStep());
            return event.getNewStep();       
        }
         public boolean intersection2Temps(Pfe e,Pfe es){
            Date date1 = e.getDatesoutenance();
            Date dateduree1=convertion(date1, e.getDureesoutenance());
            Date date2 = es.getDatesoutenance();
            Date dateduree2=convertion(date2, es.getDureesoutenance());
            if((date1.compareTo(date2)>=0&& date1.compareTo(dateduree2)<=0)
               ||(dateduree1.compareTo(date2)>=0&&dateduree1.compareTo(dateduree2)<=0) 
               ||(date2.compareTo(date1)>=0&&date2.compareTo(dateduree1)<=0)
               ||(dateduree2.compareTo(date1)>=0&&dateduree2.compareTo(dateduree1)<=0)){
                return true;          
            }         
        return false;   
    }
         public void enregistrer(){             
             selectedPfe.update();
             updateCalendar();        
         }
        
        public List<Pfe> toutPFEinterc(){
            List<Pfe> inter = new ArrayList<>();
            for(int i=0;i<toutProjetPlanifier.size();i++)
            {
                if(intersection2Temps(selectedPfe, toutProjetPlanifier.get(i)))
                    inter.add(toutProjetPlanifier.get(i));
            }
            return inter;
        }
        public boolean testexistance(Enseignant e,Pfe pfe){
            if(e!=null){
                if(e.equals(pfe.getChefjury()))
                    return true;
                if(e.equals(pfe.getEncadreur()))
                    return true;
                if(e.equals(pfe.getRapporteur()))
                    return true;        
            
            }       
        return false;
        }
        
        public boolean verifierEnseignantInList(List<Pfe> listpfe){
            for(int i=0 ;i<listpfe.size();i++){
                if(testexistance(selectedPfe.getChefjury(), listpfe.get(i)))
                    return true;
                if(testexistance(selectedPfe.getEncadreur(), listpfe.get(i)))
                    return true;
                if(testexistance(selectedPfe.getRapporteur(), listpfe.get(i)))
                    return true;
            }
            return false;
        }
        public List<Salle> eliminationSalle(List<Pfe> pfes){
           List<Salle> salles=new ArrayList<>(toutSalle);
            for(int i=0 ;i<pfes.size();i++){
                if(pfes.get(i).getSalle()!=null)
                    salles.remove(pfes.get(i).getSalle());
            }
        
        return salles;
        }
        
        public List<Enseignant> eliminationEnseigant(List<Pfe> pfes){
           List<Enseignant> enseignants=new ArrayList<>(toutenseignants);
            for(int i=0 ;i<pfes.size();i++){
                if(pfes.get(i).getChefjury()!=null)
                    enseignants.remove(pfes.get(i).getChefjury());
                if(pfes.get(i).getRapporteur()!=null)
                    enseignants.remove(pfes.get(i).getRapporteur());
                if(pfes.get(i).getEncadreur()!=null)
                    enseignants.remove(pfes.get(i).getEncadreur());
            }
            enseignants.remove(selectedPfe.getChefjury());
            enseignants.remove(selectedPfe.getEncadreur());
            enseignants.remove(selectedPfe.getRapporteur());
        return enseignants;
        }
        
        private List<Pfe> pfeintersec;
        private Boolean enregisAnnuler;
        
        public String newplanifierprojet(FlowEvent event){
            String actuel=event.getOldStep();
            String next=event.getNewStep();
            if(!actuel.equals(next)){                 
                switch(actuel){                   
                    case"Date":  
                                 pfeintersec = toutPFEinterc();
                               if(!verifierEnseignantInList(pfeintersec)){
                                   filtreSalle=eliminationSalle(pfeintersec);
                                   System.err.println("succes date"); 
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Date est acceptée"));
                                   return next;                    
                               }else
                               {                
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Enseignant non disponible"));
                                   System.err.println(" date refuser");
                               return actuel;
                               }
                        
                       
                        
                        
                    case"Salle":if(next.equals("President")){
                        filtreenseignants=eliminationEnseigant(pfeintersec) ;
                        if(selectedPfe.getChefjury()!=null){
                        filtreenseignants.add(selectedPfe.getChefjury());}
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Salle est acceptée"));
                        
                        return next;}
                        break;
                        
                        
                    case"President":if(next.equals("Rapporteur")){
                        filtreenseignants=eliminationEnseigant(pfeintersec) ;
                        if(selectedPfe.getRapporteur()!=null){
                        filtreenseignants.add(selectedPfe.getRapporteur());}
                        return next;
                    }
                        break;
                    case"Rapporteur":
                        if(next.equals("President")){
                        filtreenseignants=eliminationEnseigant(pfeintersec);
                        if(selectedPfe.getChefjury()!=null){
                        filtreenseignants.add(selectedPfe.getChefjury());}
                        }
                        break;
                    case "Confirmation" :
                        if(next.equals("Date")){
                            if(enregisAnnuler){
                                System.err.println(actuel +" vers "+next);
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "plannifier avec succes"));}
                            else{
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "operation échouée"));
                            }
                        }
                        break;             
                }            
         }else
            { FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "operation échouée"));
            }
            return event.getNewStep();        
       }
        public Boolean verifierSalleInList(List<Pfe> pfes){            
            for(int i=0 ;i<pfes.size();i++){
                if(pfes.get(i).getSalle().equals(selectedPfe.getSalle()))
                    return true;
            }       
        return false;
        }
        public void eliminerPfeSelected(){
            
            for (int i=0;i<pfeintersec.size();i++){
                if(Objects.equals(pfeintersec.get(i).getCodepfe(), selectedPfe.getCodepfe())){
                        pfeintersec.remove(i);
                break;}
                
            }
        
        }
        public String editplanifierprojet(FlowEvent event){
            String actuel=event.getOldStep();
            String next=event.getNewStep();
            System.err.println(actuel+" vers "+next);
            if(!actuel.equals(next)){
            switch(actuel){                   
                    case"Date": if(next.equals("Salle")){
                                 pfeintersec = toutPFEinterc();
                                 eliminerPfeSelected();
                                 boolean enseignant=verifierEnseignantInList(pfeintersec);
                                 boolean salle=verifierSalleInList(pfeintersec);
                               if(!enseignant&&!salle){
                                   filtreSalle=eliminationSalle(pfeintersec);
                                   System.err.println("succes date"); 
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Date est acceptée"));
                                   return next;                    
                               }else
                               {   if(salle){            
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Enseignant non disponible"));}
                                   if(enseignant){
                                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Salle non disponible")); } 
                               System.err.println(" date refuser");
                               return actuel;
                               }}break;
                    case "Salle": if(next.equals("President")){
                           filtreenseignants=eliminationEnseigant(pfeintersec);
                           filtreenseignants.add(selectedPfe.getChefjury());                    
                    }                
                        break;
                   case "President": if(next.equals("Rapporteur")){
                            filtreenseignants=eliminationEnseigant(pfeintersec);
                            filtreenseignants.add(selectedPfe.getRapporteur());                 
                   }               
                        break;
                   case "Rapporteur" :if(next.equals("President")){
                            filtreenseignants=eliminationEnseigant(pfeintersec);
                            filtreenseignants.add(selectedPfe.getChefjury());
                   }                   
                   break; 
                       case "Confirmation" :
                        if(next.equals("Date")){
                            if(enregisAnnuler){
                                System.err.println(actuel +" vers "+next);
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "modification effectuée avec succès"));}
                            else{
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "modification échouée"));
                            }
                        }
                        break;
            
                  }           
            }else
            { FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "modification échouée"));
            }
            return event.getNewStep();        
        }
        public void modifenseignant(){
            selectedPfe.update();
            System.err.println("modification avec succes");
        
        
        }
        public void annulerenseignant(){
            toutProjetNonPlanifier = new ArrayList<>(PfeControleur.getPFENonPlanifies());
            toutProjetNonPlanifierChercher = new ArrayList<>(toutProjetNonPlanifier);            
            System.err.println("modification avec succes");      
        }
        
       public void updateCalendar(){
            toutProjetNonPlanifier = new ArrayList<>(PfeControleur.getPFENonPlanifies());
            toutProjetNonPlanifierChercher = new ArrayList<>(toutProjetNonPlanifier);
            toutProjetPlanifier = new ArrayList<>(PfeControleur.getPFEPlanifies());
            //mise ajour de list projet non planifier
            afficherNonPlanifies(null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("section", selectedSection);
            mettreAJourCalander();       
       }
       
       public void mettreAJourCalander(){           
           System.err.println("mettre a jour succes: mettreAJourCalander");           
           if(selectedSection==null)
           System.err.println("section : null");
           else
               System.err.println("section :"+selectedSection.getSection());
           FacesContext context = FacesContext.getCurrentInstance();
           if(selectedSalle==null)
               System.err.println("salle : null");
           else
               System.err.println("salle :"+selectedSalle.getSalle());
           context.getExternalContext().getSessionMap().put("salle", selectedSalle);
           eventModel = new DefaultScheduleModel();
           for (Pfe p : toutProjetPlanifier) {
               if(selectedSalle==null){
                    if(selectedSection==null)
                            addprojet(p);
                    else if(Objects.equals(p.getSection(), selectedSection))
                            addprojet(p);
               }else{
                   if(selectedSalle.equals(p.getSalle())){
                       if(selectedSection==null)
                            addprojet(p);
                    else if(Objects.equals(p.getSection(), selectedSection))
                            addprojet(p);  
                       }
                   }
           }
       }
    
      public boolean verifierEnseignant(Pfe p){
        
        if(filtreListNonplanifier==null)
            return true;
        else if(filtreListNonplanifier.isEmpty())
            return true;        
        else if (filtreListNonplanifier.size() == 2) {
            return p.getChefjury() == null && p.getRapporteur() == null;
        }else
        {
            if(filtreListNonplanifier.get(0).equals("1")){
                return p.getChefjury() == null;
            }
            if(filtreListNonplanifier.get(0).equals("2")){
                return p.getRapporteur() == null;
            }
           
        }
        return false;
        }
    public boolean verifierNom(Pfe p){
        if(nomAchercher==null || "".equals(nomAchercher))
            return true;
        else
        {
            return p.getNomprojet().startsWith(nomAchercher);
        }       
    }
    //licence ou ingenierie
    public boolean verifierSection(Pfe p){
        if(selectedSection==null)
            return true;
        else if(Objects.equals(p.getSection(), selectedSection))
            return true;
        else
        return false;
}
    
    
    public void afficherNonPlanifies(AjaxBehaviorEvent  event) {
        toutProjetNonPlanifierChercher = new ArrayList<>();
        for (Pfe p : toutProjetNonPlanifier) {
            if(verifierNom(p) && verifierEnseignant(p) && verifierSection(p)){
                toutProjetNonPlanifierChercher.add(p);            
            }        
        }
    }
    
    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }
        event = new Soutenance();
    }
    
    public void onEventSelect(SelectEvent selectEvent) {
        event = (Soutenance) selectEvent.getObject();
        selectedPfe = event.getPfe();
    }

    public String getNomAchercher() {
        return nomAchercher;
    }

    public Soutenance getEvent() {
        return event;
    }

    public void setEvent(Soutenance event) {
        this.event = event;
    }
    
    

    public void setNomAchercher(String nomAchercher) {
        this.nomAchercher = nomAchercher;
    }

    public List<String> getFiltreListNonplanifier() {
        return filtreListNonplanifier;
    }

    public void setFiltreListNonplanifier(List<String> filtreListNonplanifier) {
        this.filtreListNonplanifier = filtreListNonplanifier;
    }
    
    public Salle getSelectedSalle() {
        return selectedSalle;
    }

    public void setSelectedSalle(Salle selectedSalle) {
        this.selectedSalle = selectedSalle;
    }
    
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public Pfe getSelectedPfe() {
        return selectedPfe;
    }

    public void setSelectedPfe(Pfe selectedPfe) {
        this.selectedPfe = selectedPfe;
    }    

    public List<Pfe> getToutProjetNonPlanifierChercher() {
        return toutProjetNonPlanifierChercher;
    }

    public void setToutProjetNonPlanifierChercher(List<Pfe> toutProjetNonPlanifierChercher) {
        this.toutProjetNonPlanifierChercher = toutProjetNonPlanifierChercher;
    }    
    
    public List<Pfe> getToutProjetPlanifier() {
        return toutProjetPlanifier;
    }

    public void setToutProjetPlanifier(List<Pfe> toutProjetPlanifier) {
        this.toutProjetPlanifier = toutProjetPlanifier;
    }

    public List<Pfe> getToutProjetNonPlanifier() {
        return toutProjetNonPlanifier;
    }

    public void setToutProjetNonPlanifier(List<Pfe> toutProjetNonPlanifier) {
        this.toutProjetNonPlanifier = toutProjetNonPlanifier;
    }

    public String getDuree_Soutenance() {
        return duree_Soutenance;
    }

    public void setDuree_Soutenance(String duree_Soutenance) {
        this.duree_Soutenance = duree_Soutenance;
    }

    public Date getHeure_soutenance() {
        if(heure_soutenance==null)
            heure_soutenance=new Date();        
        if(selectedPfe.getDatesoutenance()!=null){
        heure_soutenance.setHours(selectedPfe.getDatesoutenance().getHours());
        heure_soutenance.setMinutes(selectedPfe.getDatesoutenance().getMinutes());}
        else
        {heure_soutenance=null;}
        return heure_soutenance;
    }

    public void setHeure_soutenance(Date heure_soutenance) {
        selectedPfe.getDatesoutenance().setHours(heure_soutenance.getHours());
        selectedPfe.getDatesoutenance().setMinutes(heure_soutenance.getMinutes());
        this.heure_soutenance = heure_soutenance;
    }

    public Date getDate_soutenance() {
        return date_soutenance;
    }

    public void setDate_soutenance(Date date_soutenance) {
        this.date_soutenance = date_soutenance;
    }

    public List<Salle> getToutSalle() {
        return toutSalle;
    }

    public void setToutSalle(List<Salle> toutSalle) {
        this.toutSalle = toutSalle;
    }

    public List<Salle> getFiltreSalle() {
        return filtreSalle;
    }

    public void setFiltreSalle(List<Salle> filtreSalle) {
        this.filtreSalle = filtreSalle;
    }

    public List<Enseignant> getToutenseignants() {
        return toutenseignants;
    }

    public void setToutenseignants(List<Enseignant> toutenseignants) {
        this.toutenseignants = toutenseignants;
    }

    public List<Enseignant> getFiltreenseignants() {
        return filtreenseignants;
    }

    public void setFiltreenseignants(List<Enseignant> filtreenseignants) {
        this.filtreenseignants = filtreenseignants;
    }

    public Boolean isEnregisAnnuler() {
        return enregisAnnuler;
    }

    public void setEnregisAnnuler(Boolean enregisAnnuler) {
        this.enregisAnnuler = enregisAnnuler;
    }

    public List<Section> getSectionTerminal() {
        return sectionTerminal;
    }

    public void setSectionTerminal(List<Section> sectionTerminal) {
        this.sectionTerminal = sectionTerminal;
    }

    public Section getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(Section selectedSection) {
        this.selectedSection = selectedSection;
    }
    
    
    
}