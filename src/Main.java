import ast.*;
import ast.type.Type;
import org.antlr.v4.runtime.*;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;
import ast.type.ErrorHandler;
import parser.TSmmLexer;
import parser.TSmmParser;

public class Main {

	public static void main(String... args) throws Exception {
		   if (args.length<1) {
		        System.err.println("Please, pass me the input file.");
		        return;
		    }
		   		 			
		 // create a lexer that feeds off of input CharStream
		CharStream input = CharStreams.fromFileName(args[0]);
		TSmmLexer lexer = new TSmmLexer(input);

		// create a parser that feeds off the tokens buffer
		CommonTokenStream tokens = new CommonTokenStream(lexer); 
		TSmmParser parser = new TSmmParser(tokens);
		ASTNode ast = parser.program().ast;



		Visitor<Void,Void> identificationVisitor = new IdentificationVisitor();
		ast.accept(identificationVisitor, null);


		Visitor<Void,Void> lValueVisitor = new LValueVisitor(); //TODO: instanciate a new LValueVisitor
		ast.accept(lValueVisitor, null);



		Visitor<Type,Boolean> TypeCheckingVisitor = new TypeCheckingVisitor();
		ast.accept(TypeCheckingVisitor, null);





		//lValueVisitor.visit(ast);	  Incorrect use of the Visitor pattern, Fix it!

		// * Check errors
		if(ErrorHandler.getInstance().anyError()){
			// * Show errors
			ErrorHandler.getInstance().showErrors(System.err);
		}
		else{
			// * The AST is shown
			IntrospectorModel model=new IntrospectorModel("Program", ast);
			new IntrospectorView("Introspector", model);
		}
	}
}
