import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CandidatService } from 'app/entities/candidat/candidat.service';
import { ICandidat, Candidat } from 'app/shared/model/candidat.model';

describe('Service Tests', () => {
  describe('Candidat Service', () => {
    let injector: TestBed;
    let service: CandidatService;
    let httpMock: HttpTestingController;
    let elemDefault: ICandidat;
    let expectedResult: ICandidat | ICandidat[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CandidatService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Candidat(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datenais: currentDate.format(DATE_FORMAT),
            dateCreation: currentDate.format(DATE_FORMAT),
            dateModification: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Candidat', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datenais: currentDate.format(DATE_FORMAT),
            dateCreation: currentDate.format(DATE_FORMAT),
            dateModification: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate,
            dateCreation: currentDate,
            dateModification: currentDate
          },
          returnedFromService
        );

        service.create(new Candidat()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Candidat', () => {
        const returnedFromService = Object.assign(
          {
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            provenance: 'BBBBBB',
            cni: 'BBBBBB',
            telephone: 'BBBBBB',
            sexe: 'BBBBBB',
            datenais: currentDate.format(DATE_FORMAT),
            niveau: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            dateModification: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate,
            dateCreation: currentDate,
            dateModification: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Candidat', () => {
        const returnedFromService = Object.assign(
          {
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            provenance: 'BBBBBB',
            cni: 'BBBBBB',
            telephone: 'BBBBBB',
            sexe: 'BBBBBB',
            datenais: currentDate.format(DATE_FORMAT),
            niveau: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            dateModification: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate,
            dateCreation: currentDate,
            dateModification: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Candidat', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
