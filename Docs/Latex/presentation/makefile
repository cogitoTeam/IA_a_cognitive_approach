tex_cmd=pdflatex

all:
	${tex_cmd} ac_beamer.tex

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
