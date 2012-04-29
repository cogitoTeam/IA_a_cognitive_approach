tex_cmd=pdflatex


all:
	make simple
	make index
	make simple

simple:
	${tex_cmd} rapport_final_ac.tex

index:
	makeindex rapport_final_ac.glo -s rapport_final_ac.ist -t rapport_final_ac.glg -o rapport_final_ac.gls

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
