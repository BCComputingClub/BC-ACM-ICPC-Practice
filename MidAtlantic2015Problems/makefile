PROBLEMDIRS=Arrrgument Gardener Hounded Kinfolk moon OfTheChildren Refract Sequences TalkingNumbers
ALLPROBLEMS=$(PROBLEMDIRS:%=%/all)

all: docs

docs: MidAtlantic2015.pdf Practice2015.pdf Handouts2015.pdf


MidAtlantic2015.pdf: MidAtlantic2015.tex problemList.tex balloons.tex contestRules.tex $(ALLPROBLEMS)
	pdflatex MidAtlantic2015
	pdflatex MidAtlantic2015

Practice2015.pdf: Practice2015.tex contestRules.tex $(ALLPROBLEMS)
	pdflatex Practice2015
	pdflatex Practice2015

Handouts2015.pdf: Handouts2015.tex problemList.tex balloons.tex $(ALLPROBLEMS)
	pdflatex Handouts2015
	pdflatex Handouts2015

%/all:
	cd $* && $(MAKE) -$(MAKEFLAGS) all


clean:
	rm -rf *.log *.aux */*.log */*.aux */java/*.class */cpp/*.exe */*.out */*.diff */*.time
	find */cpp/* -type d | xargs rm -rf
