tex_cmd=pdflatex


all:
	make simple

double:
	make simple
	make simple

print:
	make simple_print

double_print:
	make simple_print
	make simple_print

simple:
	${tex_cmd} ac_beamer.tex

simple_print:
	${tex_cmd} ac_beamer_print.tex

clean:
	@rm *.aux
	@rm *.glg
	@rm *.glo
	@rm *.gls
	@rm *.ist
	@rm *.log
	@rm *.maf
	@rm *.mtc*
	@rm *.out
	@rm *.toc

mrproper:
	make clean
	@rm *.pdf

