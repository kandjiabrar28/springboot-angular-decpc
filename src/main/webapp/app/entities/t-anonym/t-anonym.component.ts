import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITAnonym } from 'app/shared/model/t-anonym.model';
import { TAnonymService } from './t-anonym.service';
import { TAnonymDeleteDialogComponent } from './t-anonym-delete-dialog.component';

@Component({
  selector: 'jhi-t-anonym',
  templateUrl: './t-anonym.component.html'
})
export class TAnonymComponent implements OnInit, OnDestroy {
  tAnonyms?: ITAnonym[];
  eventSubscriber?: Subscription;

  constructor(protected tAnonymService: TAnonymService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tAnonymService.query().subscribe((res: HttpResponse<ITAnonym[]>) => (this.tAnonyms = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTAnonyms();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITAnonym): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTAnonyms(): void {
    this.eventSubscriber = this.eventManager.subscribe('tAnonymListModification', () => this.loadAll());
  }

  delete(tAnonym: ITAnonym): void {
    const modalRef = this.modalService.open(TAnonymDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tAnonym = tAnonym;
  }
}
