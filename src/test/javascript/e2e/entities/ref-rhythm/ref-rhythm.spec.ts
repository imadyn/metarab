/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RefRhythmComponentsPage, RefRhythmDeleteDialog, RefRhythmUpdatePage } from './ref-rhythm.page-object';

const expect = chai.expect;

describe('RefRhythm e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let refRhythmUpdatePage: RefRhythmUpdatePage;
  let refRhythmComponentsPage: RefRhythmComponentsPage;
  let refRhythmDeleteDialog: RefRhythmDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RefRhythms', async () => {
    await navBarPage.goToEntity('ref-rhythm');
    refRhythmComponentsPage = new RefRhythmComponentsPage();
    await browser.wait(ec.visibilityOf(refRhythmComponentsPage.title), 5000);
    expect(await refRhythmComponentsPage.getTitle()).to.eq('metarabApp.refRhythm.home.title');
  });

  it('should load create RefRhythm page', async () => {
    await refRhythmComponentsPage.clickOnCreateButton();
    refRhythmUpdatePage = new RefRhythmUpdatePage();
    expect(await refRhythmUpdatePage.getPageTitle()).to.eq('metarabApp.refRhythm.home.createOrEditLabel');
    await refRhythmUpdatePage.cancel();
  });

  it('should create and save RefRhythms', async () => {
    const nbButtonsBeforeCreate = await refRhythmComponentsPage.countDeleteButtons();

    await refRhythmComponentsPage.clickOnCreateButton();
    await promise.all([
      refRhythmUpdatePage.setCodeInput('code'),
      refRhythmUpdatePage.setNameInput('name'),
      refRhythmUpdatePage.setValeurInput('valeur'),
      refRhythmUpdatePage.transformSelectLastOption(),
      refRhythmUpdatePage.parentSelectLastOption()
    ]);
    expect(await refRhythmUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await refRhythmUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await refRhythmUpdatePage.getValeurInput()).to.eq('valeur', 'Expected Valeur value to be equals to valeur');
    await refRhythmUpdatePage.save();
    expect(await refRhythmUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await refRhythmComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RefRhythm', async () => {
    const nbButtonsBeforeDelete = await refRhythmComponentsPage.countDeleteButtons();
    await refRhythmComponentsPage.clickOnLastDeleteButton();

    refRhythmDeleteDialog = new RefRhythmDeleteDialog();
    expect(await refRhythmDeleteDialog.getDialogTitle()).to.eq('metarabApp.refRhythm.delete.question');
    await refRhythmDeleteDialog.clickOnConfirmButton();

    expect(await refRhythmComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
