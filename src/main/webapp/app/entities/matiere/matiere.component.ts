import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';
import { MatiereDeleteDialogComponent } from './matiere-delete-dialog.component';

@Component({
  selector: 'jhi-matiere',
  templateUrl: './matiere.component.html'
})
export class MatiereComponent implements OnInit, OnDestroy {
  matieres?: IMatiere[];
  eventSubscriber?: Subscription;

  constructor(protected matiereService: MatiereService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMatieres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMatiere): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMatieres(): void {
    this.eventSubscriber = this.eventManager.subscribe('matiereListModification', () => this.loadAll());
  }

  delete(matiere: IMatiere): void {
    const modalRef = this.modalService.open(MatiereDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.matiere = matiere;
  }
}
