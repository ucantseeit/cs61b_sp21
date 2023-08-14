package gitlet;

import java.io.IOException;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new GitletException("Please enter a command.");
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                if (noValidateNumArgs(0, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                Repository.init();
                break;
            case "add":
                if (noValidateNumArgs(1, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                Repository.add(args[1]);
                break;
            case "commit":
                if(noValidateNumArgs(1, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                String message = args[1];
                Repository.commit(message);
                break;
            case "checkout":
                if (args.length == 3) {
                    String fileName = args[2];
                    Repository.basicCheckout(fileName);
                } else if (args.length == 4) {
                    Repository.checkoutFromCommitBefore(args[1], args[3]);
                } else if (args.length == 2) {
                    Repository.branchCheckout(args[1]);
                } else {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }

                break;
            case "rm":
                if (noValidateNumArgs(1, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                Repository.remove(args[1]);
                break;
            case "log":
                if (noValidateNumArgs(0, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                Repository.log();
                break;
            case "log-global":
                // TODO
            case "find":
                if (noValidateNumArgs(1, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                Repository.find(args[1]);
                break;
            case "status":
                // TODO
            case "branch":
                if (noValidateNumArgs(1, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                if (noValidateNumArgs(1, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                Repository.rm_branch(args[1]);
                break;
            case "reset":
                if (noValidateNumArgs(1, args.length - 1)) {
                    System.out.println("Incorrect operands");
                    System.exit(0);
                }
                Repository.reset(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
            // TODO: FILL THE REST IN
        }
    }

    private static boolean noValidateNumArgs(int exp, int real) {
        return exp != real;
    }
}
