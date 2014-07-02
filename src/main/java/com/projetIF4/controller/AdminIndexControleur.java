/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.projetIF4.controller;

import com.projetIF4.model.Pfa;
import com.projetIF4.model.Pfe;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Satellite
 */
@ManagedBean
@ViewScoped
public class AdminIndexControleur implements Serializable{

   private HorizontalBarChartModel barModel;
    private PieChartModel pieModelPfaplan, pieModelPfeplan, pieModelPfaAffec, pieModelPfeAffec;

    private PieChartModel pfaPlanModel, pfaAffectModel, pfePlanModel, pfeAffectModel;

    private List<Pfa> affectesPfa, planifiesPfa, nonAffectesPfa, nonPlanifiesPfa;
    private List<Pfe> affectesPfe, planifiesPfe, nonAffectesPfe, nonPlanifiesPfe;

    @PostConstruct
    public void init() {
        initListePfa();
        initListePfe();
        createPieModels();
        createBarModels();
    }

    private void initListePfa() {
        planifiesPfa = PfaControleur.getPFAPlanifies();
        affectesPfa = PfaControleur.getPFAAffectes();
        nonPlanifiesPfa = PfaControleur.getPFANonPlanifies();
        nonAffectesPfa = PfaControleur.getPFANonAffectes();
    }

    private void initListePfe() {
        planifiesPfe = PfeControleur.getPFEPlanifies();
        affectesPfe = PfeControleur.getPFEAffectes();
        nonPlanifiesPfe = PfeControleur.getPFENonPlanifies();
        nonAffectesPfe = PfeControleur.getPFENonAffectes();
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createPieModels() {
        createPieModel();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public PieChartModel getPfaPlanModel() {
        return pfaPlanModel;
    }

    public void setPfaPlanModel(PieChartModel pfaPlanModel) {
        this.pfaPlanModel = pfaPlanModel;
    }

    public PieChartModel getPfaAffectModel() {
        return pfaAffectModel;
    }

    public void setPfaAffectModel(PieChartModel pfaAffectModel) {
        this.pfaAffectModel = pfaAffectModel;
    }

    public PieChartModel getPfePlanModel() {
        return pfePlanModel;
    }

    public void setPfePlanModel(PieChartModel pfePlanModel) {
        this.pfePlanModel = pfePlanModel;
    }

    public PieChartModel getPfeAffectModel() {
        return pfeAffectModel;
    }

    public void setPfeAffectModel(PieChartModel pfeAffectModel) {
        this.pfeAffectModel = pfeAffectModel;
    }

    public PieChartModel getPieModelPfaplan() {
        return pieModelPfaplan;
    }

    public void setPieModelPfaplan(PieChartModel pieModelPfaplan) {
        this.pieModelPfaplan = pieModelPfaplan;
    }

    public PieChartModel getPieModelPfeplan() {
        return pieModelPfeplan;
    }

    public void setPieModelPfeplan(PieChartModel pieModelPfeplan) {
        this.pieModelPfeplan = pieModelPfeplan;
    }

    public PieChartModel getPieModelPfaAffec() {
        return pieModelPfaAffec;
    }

    public void setPieModelPfaAffec(PieChartModel pieModelPfaAffec) {
        this.pieModelPfaAffec = pieModelPfaAffec;
    }

    public PieChartModel getPieModelPfeAffec() {
        return pieModelPfeAffec;
    }

    public void setPieModelPfeAffec(PieChartModel pieModelPfeAffec) {
        this.pieModelPfeAffec = pieModelPfeAffec;
    }

    public void setBarModel(HorizontalBarChartModel barModel) {
        this.barModel = barModel;
    }

    private void createBarModel() {
        barModel = initBarModel();
        barModel.setTitle("Statistiques Générales");
        barModel.setLegendPosition("ne");

        Axis x = barModel.getAxis(AxisType.X);
        x.setLabel("Nombre"); // au dessous de l'axe Y
        x.setMin(0);
        x.setMax(500);
    }

    private HorizontalBarChartModel initBarModel() {
        HorizontalBarChartModel model = new HorizontalBarChartModel();
        ChartSeries stat = new ChartSeries();
        stat.setLabel("Nombre");
        stat.set("Salles", 7);
        stat.set("Etudiants", 300);
        stat.set("Enseignants", 50);

        model.addSeries(stat);

        return model;
    }

    private void createPieModel() {
        pieModelPfaAffec = new PieChartModel();
        pieModelPfaAffec.set("Affectés", affectesPfa.size());
        pieModelPfaAffec.set("Non Affectés", nonAffectesPfa.size());
        pieModelPfaAffec.setTitle("Affectation PFA");
        pieModelPfaAffec.setShowDataLabels(true);
        pieModelPfaAffec.setLegendPosition("w");

        pieModelPfeAffec = new PieChartModel();
        pieModelPfeAffec.set("Affectés", affectesPfe.size());
        pieModelPfeAffec.set("Non Affectés", nonAffectesPfe.size());
        pieModelPfeAffec.setTitle("Affectation PFE");
        pieModelPfeAffec.setShowDataLabels(true);
        pieModelPfeAffec.setLegendPosition("w");

        pieModelPfaplan = new PieChartModel();
        pieModelPfaplan.set("Planifiés", planifiesPfa.size());
        pieModelPfaplan.set("Non Planifiés", nonPlanifiesPfa.size());
        pieModelPfaplan.setTitle("Planification PFA");
        pieModelPfaplan.setShowDataLabels(true);
        pieModelPfaplan.setLegendPosition("w");

        pieModelPfeplan = new PieChartModel();
        pieModelPfeplan.set("Planifiés", planifiesPfe.size());
        pieModelPfeplan.set("Non Planifiés", nonPlanifiesPfe.size());
        pieModelPfeplan.setTitle("Planification PFE");
        pieModelPfeplan.setShowDataLabels(true);
        pieModelPfeplan.setLegendPosition("w");

    }

    public void selectPfaAffec(ItemSelectEvent event) {
        pfaAffectModel = new PieChartModel();

        int nbif4 = 0;

        // pfa affectes
        if (event.getItemIndex() == 0) {
            pfaAffectModel.setTitle("PFA affectés");
            for (Pfa p : affectesPfa) {
                if (p.getSection().getSection().equals("IF4")) {
                    nbif4++;
                }
            }
        } // pfa non affectes
        else if (event.getItemIndex() == 1) {
            pfaAffectModel.setTitle("PFA non affectés");
            for (Pfa p : nonAffectesPfa) {
                if (p.getSection().getSection().equals("IF4")) {
                    nbif4++;
                }
            }
        }
        // construire un graphe
        pfaAffectModel.set("IF4", nbif4);
        pfaAffectModel.setShowDataLabels(true);
        pfaAffectModel.setLegendPosition("nw");

    }

    public void selectPfeAffec(ItemSelectEvent event) {
        pfeAffectModel = new PieChartModel();
        int nbif5 = 0, nblfi3 = 0;
        // pfa affectes
        if (event.getItemIndex() == 0) {
            pfeAffectModel.setTitle("PFE affectés");
            for (Pfe p : affectesPfe) {
                switch (p.getSection().getSection()) {
                    case "IF5":
                        nbif5++;
                        break;
                    case "LFI3":
                        nblfi3++;
                        break;
                }
            }
        } // pfa non affectes
        else if (event.getItemIndex() == 1) {
            pfeAffectModel.setTitle("PFE non affectés");
            for (Pfe p : nonAffectesPfe) {
                switch (p.getSection().getSection()) {
                    case "IF5":
                        nbif5++;
                        break;
                    case "LFI3":
                        nblfi3++;
                        break;
                }
            }
        }

        // dessiner le graphe
        pfeAffectModel.set("IF5", nbif5);
        pfeAffectModel.set("LFI3", nblfi3);
        pfeAffectModel.setShowDataLabels(true);
        pfeAffectModel.setLegendPosition("nw");

    }

    public void selectPfaPlan(ItemSelectEvent event) {
        pfaPlanModel = new PieChartModel();
        int nbif4 = 0;
        // pfa affectes
        if (event.getItemIndex() == 0) {
            pfaPlanModel.setTitle("PFA planifiés");
            for (Pfa p : planifiesPfa) {
                if (p.getSection().getSection().equals("IF4")) {
                    nbif4++;
                }
            }
        } // pfa non affectes
        else if (event.getItemIndex() == 1) {
            pfaPlanModel.setTitle("PFA non planifiés");
            for (Pfa p : planifiesPfa) {
                if (p.getSection().getSection().equals("IF4")) {
                    nbif4++;
                }
            }
        }

        pfaPlanModel.set("IF4", nbif4);
        pfaPlanModel.setShowDataLabels(true);
        pfaPlanModel.setLegendPosition("nw");

    }

    public void selectPfePlan(ItemSelectEvent event) {
        pfePlanModel = new PieChartModel();
        int nbif5 = 0, nblfi3 = 0,index = event.getItemIndex();

        // pfa affectes
        if (index == 0) {
            pfePlanModel.setTitle("PFE planifiés");
            for (Pfe p : planifiesPfe) {
                switch (p.getSection().getSection()) {
                    case "IF5":
                        nbif5++;
                        break;
                    case "LFI3":
                        nblfi3++;
                        break;
                }
            }
        } // pfa non affectes
        else if (index == 1) {
            pfePlanModel.setTitle("PFE non planifiés");
            for (Pfe p : nonPlanifiesPfe) {
                switch (p.getSection().getSection()) {
                    case "IF5":
                        nbif5++;
                        break;
                    case "LFI3":
                        nblfi3++;
                        break;
                }
            }
        }

        // dessiner le graphe 
        pfePlanModel.set("IF5", nbif5);
        pfePlanModel.set("LFI3", nblfi3);
        pfePlanModel.setShowDataLabels(true);
        pfePlanModel.setLegendPosition("nw");

    }
}
