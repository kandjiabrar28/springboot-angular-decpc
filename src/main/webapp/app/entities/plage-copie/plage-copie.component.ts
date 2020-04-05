import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlageCopie } from 'app/shared/model/plage-copie.model';
import { PlageCopieService } from './plage-copie.service';
import { PlageCopieDeleteDialogComponent } from './plage-copie-delete-dialog.component';

@Component({
  selector: 'jhi-plage-copie',
  templateUrl: './plage-copie.component.html'
})
export class PlageCopieComponent implements OnInit, OnDestroy {
  plageCopies?: IPlageCopie[];
  eventSubscriber?: Subscription;

  constructor(protected plageCopieService: PlageCopieService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.plageCopieService.query().subscribe((res: HttpResponse<IPlageCopie[]>) => (this.plageCopies = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlageCopies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlageCopie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlageCopies(): void {
    this.eventSubscriber = this.eventManager.subscribe('plageCopieListModification', () => this.loadAll());
  }

  delete(plageCopie: IPlageCopie): void {
    const modalRef = this.modalService.open(PlageCopieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plageCopie = plageCopie;
  }
}
