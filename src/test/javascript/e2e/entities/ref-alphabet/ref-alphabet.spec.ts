/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RefAlphabetComponentsPage, RefAlphabetDeleteDialog, RefAlphabetUpdatePage } from './ref-alphabet.page-object';

const expect = chai.expect;

describe('RefAlphabet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let refAlphabetUpdatePage: RefAlphabetUpdatePage;
  let refAlphabetComponentsPage: RefAlphabetComponentsPage;
  let refAlphabetDeleteDialog: RefAlphabetDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RefAlphabets', async () => {
    await navBarPage.goToEntity('ref-alphabet');
    refAlphabetComponentsPage = new RefAlphabetComponentsPage();
    await browser.wait(ec.visibilityOf(refAlphabetComponentsPage.title), 5000);
    expect(await refAlphabetComponentsPage.getTitle()).to.eq('metarabApp.refAlphabet.home.title');
  });

  it('should load create RefAlphabet page', async () => {
    await refAlphabetComponentsPage.clickOnCreateButton();
    refAlphabetUpdatePage = new RefAlphabetUpdatePage();
    expect(await refAlphabetUpdatePage.getPageTitle()).to.eq('metarabApp.refAlphabet.home.createOrEditLabel');
    await refAlphabetUpdatePage.cancel();
  });

  it('should create and save RefAlphabets', async () => {
    const nbButtonsBeforeCreate = await refAlphabetComponentsPage.countDeleteButtons();

    await refAlphabetComponentsPage.clickOnCreateButton();
    await promise.all([
      refAlphabetUpdatePage.setCodeInput('code'),
      refAlphabetUpdatePage.setNameInput('name'),
      refAlphabetUpdatePage.setRhythmInput('rhythm'),
      refAlphabetUpdatePage.languageSelectLastOption()
    ]);
    expect(await refAlphabetUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await refAlphabetUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await refAlphabetUpdatePage.getRhythmInput()).to.eq('rhythm', 'Expected Rhythm value to be equals to rhythm');
    await refAlphabetUpdatePage.save();
    expect(await refAlphabetUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await refAlphabetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RefAlphabet', async () => {
    const nbButtonsBeforeDelete = await refAlphabetComponentsPage.countDeleteButtons();
    await refAlphabetComponentsPage.clickOnLastDeleteButton();

    refAlphabetDeleteDialog = new RefAlphabetDeleteDialog();
    expect(await refAlphabetDeleteDialog.getDialogTitle()).to.eq('metarabApp.refAlphabet.delete.question');
    await refAlphabetDeleteDialog.clickOnConfirmButton();

    expect(await refAlphabetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
