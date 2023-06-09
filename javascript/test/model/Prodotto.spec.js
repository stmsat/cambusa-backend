/*
 * cambusa-controller
 * Servizio per la tracciatura delle scadenze dei prodotti
 *
 * OpenAPI spec version: 0.0.4
 * Contact: stmsat@gmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 *
 * Swagger Codegen version: 3.0.42
 *
 * Do not edit the class manually.
 *
 */
(function(root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD.
    define(['expect.js', '../../src/index'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    factory(require('expect.js'), require('../../src/index'));
  } else {
    // Browser globals (root is window)
    factory(root.expect, root.CambusaController);
  }
}(this, function(expect, CambusaController) {
  'use strict';

  var instance;

  describe('(package)', function() {
    describe('Prodotto', function() {
      beforeEach(function() {
        instance = new CambusaController.Prodotto();
      });

      it('should create an instance of Prodotto', function() {
        // TODO: update the code to test Prodotto
        expect(instance).to.be.a(CambusaController.Prodotto);
      });

      it('should have the property id (base name: "id")', function() {
        // TODO: update the code to test the property id
        expect(instance).to.have.property('id');
        // expect(instance.id).to.be(expectedValueLiteral);
      });

      it('should have the property name (base name: "name")', function() {
        // TODO: update the code to test the property name
        expect(instance).to.have.property('name');
        // expect(instance.name).to.be(expectedValueLiteral);
      });

      it('should have the property dataScadenza (base name: "dataScadenza")', function() {
        // TODO: update the code to test the property dataScadenza
        expect(instance).to.have.property('dataScadenza');
        // expect(instance.dataScadenza).to.be(expectedValueLiteral);
      });

      it('should have the property tipo (base name: "tipo")', function() {
        // TODO: update the code to test the property tipo
        expect(instance).to.have.property('tipo');
        // expect(instance.tipo).to.be(expectedValueLiteral);
      });

      it('should have the property posizione (base name: "posizione")', function() {
        // TODO: update the code to test the property posizione
        expect(instance).to.have.property('posizione');
        // expect(instance.posizione).to.be(expectedValueLiteral);
      });

      it('should have the property aperto (base name: "aperto")', function() {
        // TODO: update the code to test the property aperto
        expect(instance).to.have.property('aperto');
        // expect(instance.aperto).to.be(expectedValueLiteral);
      });

      it('should have the property dataApertura (base name: "dataApertura")', function() {
        // TODO: update the code to test the property dataApertura
        expect(instance).to.have.property('dataApertura');
        // expect(instance.dataApertura).to.be(expectedValueLiteral);
      });

      it('should have the property quantita (base name: "quantita")', function() {
        // TODO: update the code to test the property quantita
        expect(instance).to.have.property('quantita');
        // expect(instance.quantita).to.be(expectedValueLiteral);
      });

      it('should have the property dataScadenzaGenerata (base name: "dataScadenzaGenerata")', function() {
        // TODO: update the code to test the property dataScadenzaGenerata
        expect(instance).to.have.property('dataScadenzaGenerata');
        // expect(instance.dataScadenzaGenerata).to.be(expectedValueLiteral);
      });

    });
  });

}));
