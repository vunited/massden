#!/usr/bin/env bash

#Appendix C
sudo sh /opt/solr/bin/solr create_collection -c solrExchange -d /vagrant/appendixC/cores/solrexchange/conf/ -n solrExchange
curl http://localhost:8983/solr/solrExchange/dataimport?command=full-import
