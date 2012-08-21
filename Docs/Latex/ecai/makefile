tex_cmd=pdflatex

simple:
	${tex_cmd} ecai_beamer.tex

all:
	make simple
	make print

all_double:
	make all
	make all

double:
	make simple
	make simple

print:
	make simple_print

double_print:
	make simple_print
	make simple_print

simple_print:
	${tex_cmd} ecai_beamer_print.tex

help:
	@cat makefile
	@echo
	@echo Débrouille toi avec ça!

clean:
	@rm *.aux -f
	@rm *.glg -f
	@rm *.glo -f
	@rm *.gls -f
	@rm *.ist -f
	@rm *.log -f
	@rm *.maf -f
	@rm *.mtc* -f
	@rm *.out -f
	@rm *.toc -f
	@rm *.log -f
	@rm *.snm -f
	@rm *.toc -f
	@rm *.nav -f

mrproper:
	make clean
	@rm *.pdf -f

