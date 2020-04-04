import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICorrecteur } from 'app/shared/model/correcteur.model';
import { CorrecteurService } from './correcteur.service';
import { CorrecteurDeleteDialogComponent } from './correcteur-delete-dialog.component';

@Component({
  selector: 'jhi-correcteur',
  templateUrl: './correcteur.component.html'
})
export class CorrecteurComponent implements OnInit, OnDestroy {
  correcteurs?: ICorrecteur[];
  eventSubscriber?: Subscription;

  constructor(protected correcteurService: CorrecteurService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.correcteurService.query().subscribe((res: HttpResponse<ICorrecteur[]>) => (this.correcteurs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCorrecteurs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICorrecteur): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCorrecteurs(): void {
    this.eventSubscriber = this.eventManager.subscribe('correcteurListModification', () => this.loadAll());
  }

  delete(correcteur: ICorrecteur): void {
    const modalRef = this.modalService.open(CorrecteurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.correcteur = correcteur;
  }
}
