import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'candidat',
        loadChildren: () => import('./candidat/candidat.module').then(m => m.JhipsterCandidatModule)
      },
      {
        path: 'centre',
        loadChildren: () => import('./centre/centre.module').then(m => m.JhipsterCentreModule)
      },
      {
        path: 'correcteur',
        loadChildren: () => import('./correcteur/correcteur.module').then(m => m.JhipsterCorrecteurModule)
      },
      {
        path: 'examen',
        loadChildren: () => import('./examen/examen.module').then(m => m.JhipsterExamenModule)
      },
      {
        path: 'jury',
        loadChildren: () => import('./jury/jury.module').then(m => m.JhipsterJuryModule)
      },
      {
        path: 'matiere',
        loadChildren: () => import('./matiere/matiere.module').then(m => m.JhipsterMatiereModule)
      },
      {
        path: 'note',
        loadChildren: () => import('./note/note.module').then(m => m.JhipsterNoteModule)
      },
      {
        path: 'plage',
        loadChildren: () => import('./plage/plage.module').then(m => m.JhipsterPlageModule)
      },
      {
        path: 'pv-surveillance',
        loadChildren: () => import('./pv-surveillance/pv-surveillance.module').then(m => m.JhipsterPVSurveillanceModule)
      },
      {
        path: 'salle',
        loadChildren: () => import('./salle/salle.module').then(m => m.JhipsterSalleModule)
      },
      {
        path: 'surveillant',
        loadChildren: () => import('./surveillant/surveillant.module').then(m => m.JhipsterSurveillantModule)
      },
      {
        path: 'tables',
        loadChildren: () => import('./tables/tables.module').then(m => m.JhipsterTablesModule)
      },
      {
        path: 't-anonym',
        loadChildren: () => import('./t-anonym/t-anonym.module').then(m => m.JhipsterTAnonymModule)
      },
      {
        path: 'tour',
        loadChildren: () => import('./tour/tour.module').then(m => m.JhipsterTourModule)
      },
      {
        path: 'anonymat',
        loadChildren: () => import('./anonymat/anonymat.module').then(m => m.JhipsterAnonymatModule)
      },
      {
        path: 'specialite',
        loadChildren: () => import('./specialite/specialite.module').then(m => m.JhipsterSpecialiteModule)
      },
      {
        path: 'session',
        loadChildren: () => import('./session/session.module').then(m => m.JhipsterSessionModule)
      },
      {
        path: 'niveau',
        loadChildren: () => import('./niveau/niveau.module').then(m => m.JhipsterNiveauModule)
      },
      {
        path: 'filiere',
        loadChildren: () => import('./filiere/filiere.module').then(m => m.JhipsterFiliereModule)
      },
      {
        path: 'table',
        loadChildren: () => import('./table/table.module').then(m => m.JhipsterTableModule)
      },
      {
        path: 'plage-copie',
        loadChildren: () => import('./plage-copie/plage-copie.module').then(m => m.JhipsterPlageCopieModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterEntityModule {}
