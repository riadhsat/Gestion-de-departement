
package com.projetIF4.planning;

import com.projetIF4.controller.EnseignantControleur;
import com.projetIF4.controller.PfaControleur;
import com.projetIF4.controller.SalleControleur;
import com.projetIF4.controller.SectionControleur;
import com.projetIF4.model.Enseignant;
import com.projetIF4.model.Pfa;
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
public class PlanningPFAcontroleur implements Serializable{
    
    
    
    //les variable de calendrier
    private ScheduleModel eventModel;
    //salle selectionne pour affichage
    private Salle selectedSalle;
     private Section selectedSection;
    
    private List<Pfa> toutProjetPlanifier;
    private List<Pfa> toutProjetNonPlanifier;
    private List<Pfa> toutProjetNonPlanifierChercher;
    
     //pfe selection
    private Pfa selectedPfa;
    
    //les attribue pour  recherche:
    //nom a chercher
    private String nomAchercher;
    
    
    private String duree_Soutenance;
    private Date heure_soutenance;
    private Date date_soutenance;
    
    private List<Salle> toutSalle;    
    private List<Salle> filtreSalle;
    private List<Enseignant> toutenseignants;
    
    private Boolean enregisAnnuler;
    
    
    private Soutenance event = new Soutenance();
    
    private List<Section> sectionNonTerminal;
    
    public PlanningPFAcontroleur() {        
        nomAchercher="";        
        FacesContext context = FacesContext.getCurrentInstance();
        selectedSalle =(Salle)context.getExternalContext().getSessionMap().get("salle");
        selectedSection=(Section)context.getExternalContext().getSessionMap().get("section");
    }
    
    
        @PostConstruct
        public void postConstruction(){
            toutSalle=SalleControleur.getAllSalles();
            toutenseignants=EnseignantControleur.getAllEnseignant();
            sectionNonTerminal=SectionControleur.getAllSectionTerminal(false);
            updateCalendar();              
        }
        
        
        public void updateCalendar(){
            toutProjetNonPlanifier = new ArrayList<>(PfaControleur.getPFANonPlanifies());
            toutProjetNonPlanifierChercher = new ArrayList<>(toutProjetNonPlanifier);
            toutProjetPlanifier = new ArrayList<>(PfaControleur.getPFAPlanifies());
             FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("section", selectedSection);
            afficherNonPlanifies(null);
            mettreAJourCalander();       
       }
        
         public void addprojet(Pfa p){
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
           for (Pfa p : toutProjetPlanifier) {
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
    
    
    public boolean verifierNom(Pfa p){
        if(nomAchercher==null || "".equals(nomAchercher))
            return true;
        else
        {
            return p.getNomprojet().startsWith(nomAchercher);
        }       
    }
    public boolean verifierSection(Pfa p){
        if(selectedSection==null)
            return true;
        else if(Objects.equals(p.getSection(), selectedSection))
            return true;
        else
        return false;
}
    
    public void afficherNonPlanifies(AjaxBehaviorEvent  event) {
        
        toutProjetNonPlanifierChercher = new ArrayList<>();
        for (Pfa p : toutProjetNonPlanifier) {
            if(verifierNom(p)&& verifierSection(p)){
                toutProjetNonPlanifierChercher.add(p);            
            }        
        }   
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
    public boolean verifierEnseignantInList(List<Pfa> listpfa){
            for(int i=0 ;i<listpfa.size();i++){
                if(selectedPfa.getEncardeur().equals(listpfa.get(i).getEncardeur()))
                    return true;
            }
            return false;
        }
    
    public boolean intersection2Temps(Pfa e,Pfa es){
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
    public List<Pfa> toutPFAinterc(){
            List<Pfa> inter = new ArrayList<>();
            for(int i=0;i<toutProjetPlanifier.size();i++)
            {
                if(intersection2Temps(selectedPfa, toutProjetPlanifier.get(i)))
                    inter.add(toutProjetPlanifier.get(i));
            }
            return inter;
        }
     public List<Salle> eliminationSalle(List<Pfa> pfas){
           List<Salle> salles=new ArrayList<>(toutSalle);
            for(int i=0 ;i<pfas.size();i++){
                if(pfas.get(i).getSalle()!=null)
                    salles.remove(pfas.get(i).getSalle());
            }
        
        return salles;
        }
    private List<Pfa> pfaintersec;
    public String newplanifierprojet(FlowEvent event){
            String actuel=event.getOldStep();
            String next=event.getNewStep();
            System.err.println(""+actuel+"vers"+next);
            if(!actuel.equals(next)){
                switch(actuel){
                    case"Date":
                        pfaintersec = toutPFAinterc();
                        if(!verifierEnseignantInList(pfaintersec)){
                            filtreSalle=eliminationSalle(pfaintersec);
                            System.err.println("succes date"); 
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Date est acceptée"));
                            return next;                        
                        }else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Enseignant non disponible"));
                                   System.err.println(" date refuser");
                               return actuel;                        
                        }
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
            }  
    return next;}
    
     public void enregistrer(){             
             selectedPfa.update();
             updateCalendar();        
         }
    
    public void annuler(){
            toutProjetNonPlanifier = new ArrayList<>(PfaControleur.getPFANonPlanifies());
            toutProjetNonPlanifierChercher = new ArrayList<>(toutProjetNonPlanifier);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Operation échouée"));
            System.err.println("modification avec succes");      
        }
    
    public String update(){
        Date date = selectedPfa.getDatesoutenance();
        boolean disponile=true;
        String message="";
        if (date != null&& selectedPfa.getSalle()!=null && heure_soutenance!=null) {
            Date date_final = new Date(heure_soutenance.getTime() + date.getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date_final);
            cal.add(Calendar.HOUR, 1);
            selectedPfa.setDatesoutenance(cal.getTime());
            selectedPfa.setDureesoutenance(duree_Soutenance);
            for (Pfa p : toutProjetPlanifier){
                if(!selectedPfa.equals(p)){
                    //salle indisponible
                    if(intersection2Temps(selectedPfa,p)
                       && p.getSalle().equals(selectedPfa.getSalle())){
                        disponile=false;
                        message=message+" Salle "+selectedPfa.getSalle().getSalle()+" est  indisponible";
                    }
                    System.err.println("pfa encad:"+selectedPfa.getEncardeur().getCinEnseignant());
                    //enseignant indisponible
                     if(intersection2Temps(selectedPfa,p)                       
                            &&p.getEncardeur().getCinEnseignant()==selectedPfa.getEncardeur().getCinEnseignant()){
                        disponile=false;
                        message=message+"\n Enseignant "+selectedPfa.getEncardeur().getNom()+" est indisponible";
                         System.err.println("enseignant indis");
                    }
                    if(!disponile)
                    break;                    
                }                
            }
            if(disponile){
                if (selectedPfa.update()) {
                    System.err.println("update avec succes");
                    updateCalendar();
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("messageDialog");
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedPfa");
                }else{
                    System.err.println("erreur lors de mise a jour");
                }
            }else{
                System.err.println("message :"+message);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("messageDialog",message);
                context.getExternalContext().getSessionMap().put("selectedPfa",selectedPfa);
            }            
        }else{
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("messageDialog","la date et la salle doit être indiquée");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPfa",selectedPfa);        
        }
        updateCalendar(); 
        return "pretty:AdminGestionPlanningPFA";
    }
    
    public void eliminerPfaSelected(){
            
            for (int i=0;i<pfaintersec.size();i++){
                if(Objects.equals(pfaintersec.get(i).getCodepfa(), selectedPfa.getCodepfa())){
                        pfaintersec.remove(i);
                break;}               
            }        
        }
    public Boolean verifierSalleInList(List<Pfa> pfas){            
            for(int i=0 ;i<pfas.size();i++){
                if(pfas.get(i).getSalle().equals(selectedPfa.getSalle()))
                    return true;
            }       
        return false;
        }
        
    public String editplanifierprojet(FlowEvent event){        
        String actuel=event.getOldStep();
        String next=event.getNewStep();
        
        if(!actuel.equals(next)){
            switch(actuel){                   
                    case"Date": if(next.equals("Salle")){
                                 pfaintersec = toutPFAinterc();
                                 eliminerPfaSelected();
                                 boolean enseignant=verifierEnseignantInList(pfaintersec);
                                 boolean salle=verifierSalleInList(pfaintersec);
                                 if(!enseignant&&!salle){
                                   filtreSalle=eliminationSalle(pfaintersec);
                                   System.err.println("succes date"); 
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Date est acceptée"));
                                   return next;
                                 }else {
                                      if(salle){            
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Enseignant non disponible"));}
                                   if(enseignant){
                                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Salle non disponible")); } 
                               System.err.println(" date refuser");
                               return actuel;                              
                                 }
                    }
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
        
        
        
        
        }
    
    
     return event.getNewStep();
    }
            
    
    public void annulerProjet(){            
        selectedPfa.setDatesoutenance(null);
        selectedPfa.setSalle(null);
        selectedPfa.setDureesoutenance(null);
        selectedPfa.update();
        updateCalendar();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Planification est annulé"));
    }
    public void quitterProjet(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("messageDialog");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedPfa");
    }
             
    public void onEventSelect(SelectEvent selectEvent) {
        event = (Soutenance) selectEvent.getObject();
        selectedPfa = event.getPfa();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public Salle getSelectedSalle() {
        return selectedSalle;
    }

    public void setSelectedSalle(Salle selectedSalle) {
        this.selectedSalle = selectedSalle;
    }

    public List<Pfa> getToutProjetPlanifier() {
        return toutProjetPlanifier;
    }

    public void setToutProjetPlanifier(List<Pfa> toutProjetPlanifier) {
        this.toutProjetPlanifier = toutProjetPlanifier;
    }

    public List<Pfa> getToutProjetNonPlanifier() {
        return toutProjetNonPlanifier;
    }

    public void setToutProjetNonPlanifier(List<Pfa> toutProjetNonPlanifier) {
        this.toutProjetNonPlanifier = toutProjetNonPlanifier;
    }

    public List<Pfa> getToutProjetNonPlanifierChercher() {
        return toutProjetNonPlanifierChercher;
    }

    public void setToutProjetNonPlanifierChercher(List<Pfa> toutProjetNonPlanifierChercher) {
        this.toutProjetNonPlanifierChercher = toutProjetNonPlanifierChercher;
    }

    public Pfa getSelectedPfa() {
        return selectedPfa;
    }

    public void setSelectedPfa(Pfa selectedPfa) {
        this.selectedPfa = selectedPfa;
    }

    public String getNomAchercher() {
        return nomAchercher;
    }

    public void setNomAchercher(String nomAchercher) {
        this.nomAchercher = nomAchercher;
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
        if(selectedPfa.getDatesoutenance()!=null){
        heure_soutenance.setHours(selectedPfa.getDatesoutenance().getHours());
        heure_soutenance.setMinutes(selectedPfa.getDatesoutenance().getMinutes());}
        else
        {heure_soutenance=null;}
        
        
        return heure_soutenance;
    }

    public void setHeure_soutenance(Date heure_soutenance) {
        selectedPfa.getDatesoutenance().setHours(heure_soutenance.getHours());
        selectedPfa.getDatesoutenance().setMinutes(heure_soutenance.getMinutes());
        this.heure_soutenance = heure_soutenance;
    }

    public Date getDate_soutenance() {
        return date_soutenance;
    }

    public void setDate_soutenance(Date date_soutenance) {
        this.date_soutenance = date_soutenance;
    }

    public Soutenance getEvent() {
        return event;
    }

    public void setEvent(Soutenance event) {
        this.event = event;
    }

    public List<Salle> getFiltreSalle() {
        return filtreSalle;
    }

    public void setFiltreSalle(List<Salle> filtreSalle) {
        this.filtreSalle = filtreSalle;
    }

    public Boolean isEnregisAnnuler() {
        return enregisAnnuler;
    }

    public void setEnregisAnnuler(Boolean enregisAnnuler) {
        this.enregisAnnuler = enregisAnnuler;
    }

    public List<Salle> getToutSalle() {
        return toutSalle;
    }

    public void setToutSalle(List<Salle> toutSalle) {
        this.toutSalle = toutSalle;
    }

    public List<Enseignant> getToutenseignants() {
        return toutenseignants;
    }

    public void setToutenseignants(List<Enseignant> toutenseignants) {
        this.toutenseignants = toutenseignants;
    }

    public List<Section> getSectionNonTerminal() {
        return sectionNonTerminal;
    }

    public void setSectionNonTerminal(List<Section> sectionNonTerminal) {
        this.sectionNonTerminal = sectionNonTerminal;
    }

    public Section getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(Section selectedSection) {
        this.selectedSection = selectedSection;
    }
    
    
    
}