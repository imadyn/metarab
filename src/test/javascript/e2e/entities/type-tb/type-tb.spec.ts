/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TypeTBComponentsPage, TypeTBDeleteDialog, TypeTBUpdatePage } from './type-tb.page-object';

const expect = chai.expect;

describe('TypeTB e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let typeTBUpdatePage: TypeTBUpdatePage;
  let typeTBComponentsPage: TypeTBComponentsPage;
  let typeTBDeleteDialog: TypeTBDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TypeTBS', async () => {
    await navBarPage.goToEntity('type-tb');
    typeTBComponentsPage = new TypeTBComponentsPage();
    await browser.wait(ec.visibilityOf(typeTBComponentsPage.title), 5000);
    expect(await typeTBComponentsPage.getTitle()).to.eq('metarabApp.typeTB.home.title');
  });

  it('should load create TypeTB page', async () => {
    await typeTBComponentsPage.clickOnCreateButton();
    typeTBUpdatePage = new TypeTBUpdatePage();
    expect(await typeTBUpdatePage.getPageTitle()).to.eq('metarabApp.typeTB.home.createOrEditLabel');
    await typeTBUpdatePage.cancel();
  });

  it('should create and save TypeTBS', async () => {
    const nbButtonsBeforeCreate = await typeTBComponentsPage.countDeleteButtons();

    await typeTBComponentsPage.clickOnCreateButton();
    await promise.all([
      typeTBUpdatePage.setCodeInput('code'),
      typeTBUpdatePage.setOrdreInput('ordre'),
      typeTBUpdatePage.typeSelectLastOption(),
      typeTBUpdatePage.refBahrSelectLastOption(),
      typeTBUpdatePage.refRhythmSelectLastOption()
    ]);
    expect(await typeTBUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await typeTBUpdatePage.getOrdreInput()).to.eq('ordre', 'Expected Ordre value to be equals to ordre');
    await typeTBUpdatePage.save();
    expect(await typeTBUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await typeTBComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TypeTB', async () => {
    const nbButtonsBeforeDelete = await typeTBComponentsPage.countDeleteButtons();
    await typeTBComponentsPage.clickOnLastDeleteButton();

    typeTBDeleteDialog = new TypeTBDeleteDialog();
    expect(await typeTBDeleteDialog.getDialogTitle()).to.eq('metarabApp.typeTB.delete.question');
    await typeTBDeleteDialog.clickOnConfirmButton();

    expect(await typeTBComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
